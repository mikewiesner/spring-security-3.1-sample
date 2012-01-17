package holiday.controller;

import holiday.domain.HolidayRequest;
import holiday.forms.HolidayRequestUpdateForm;
import holiday.security.UserContextService;
import holiday.service.HolidayService;

import java.io.UnsupportedEncodingException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@RequestMapping("/holidayrequests")
@Controller
@SessionAttributes({"holidayRequestUpdateForm","holidayRequest"})
public class HolidayRequestController {

	@Autowired
	private GenericConversionService conversionService;

	@Autowired
	private HolidayService holidayService;
	
	@Autowired
	private UserContextService userContextService;

	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid HolidayRequest holidayRequest,
			BindingResult result, Model model, HttpServletRequest request) {
		if (result.hasErrors()) {
			model.addAttribute("holidayRequest", holidayRequest);
			addDateTimeFormatPatterns(model);
			return "holidayrequests/create";
		}
		holidayService.create(holidayRequest);
		return "redirect:/holidayrequests/"
				+ encodeUrlPathSegment(holidayRequest.getId().toString(),
						request);
	}

	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String createForm(Model model) {
		HolidayRequest object = new HolidayRequest();
		
		object.setEmployee(userContextService.getCurrentUser());
		model.addAttribute("holidayRequest", object);
		addDateTimeFormatPatterns(model);
		return "holidayrequests/create";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, Model model) {
		addDateTimeFormatPatterns(model);
		model.addAttribute("holidayrequest",
				holidayService.findHolidayRequest(id));
		model.addAttribute("itemId", id);
		return "holidayrequests/show";
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("holidayrequests",
				holidayService.findAllHolidayRequests());
		addDateTimeFormatPatterns(model);
		return "holidayrequests/list";
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String update(@Valid HolidayRequestUpdateForm holidayRequest,
			BindingResult result, Model model, HttpServletRequest request) {
		if (result.hasErrors()) {
			model.addAttribute("holidayRequestUpdateForm", holidayRequest);
			addDateTimeFormatPatterns(model);
			return "holidayrequests/update";
		}
		holidayService.update(holidayRequest.returnHolidayRequest());
		return "redirect:/holidayrequests/"
				+ encodeUrlPathSegment(holidayRequest.getId().toString(),
						request);
	}

	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		HolidayRequest object = holidayService.findHolidayRequest(id);
		HolidayRequestUpdateForm form = new HolidayRequestUpdateForm();
		form.setHolidayRequest(object);
	//	model.addAttribute("holidayRequestUpdate", object);
		model.addAttribute("holidayRequestUpdateForm", form);
		addDateTimeFormatPatterns(model);
		return "holidayrequests/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") Long id, Model model) {
		HolidayRequest holidayRequest = holidayService.findHolidayRequest(id);
		holidayService.cancel(holidayRequest);
		return "redirect:/holidayrequests";
	}

	Converter<HolidayRequest, String> getHolidayRequestConverter() {
		return new Converter<HolidayRequest, String>() {
			public String convert(HolidayRequest holidayRequest) {
				return new StringBuilder().append(holidayRequest.getEmployee())
						.append(" ").append(holidayRequest.getFromDate())
						.append(" ").append(holidayRequest.getToDate())
						.toString();
			}
		};
	}

	@PostConstruct
	void registerConverters() {
		conversionService.addConverter(getHolidayRequestConverter());
	}

//	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAllowedFields("comment");

	}

	void addDateTimeFormatPatterns(Model model) {
		model.addAttribute(
				"holidayRequest_fromdate_date_format",
				DateTimeFormat.patternForStyle("S-",
						LocaleContextHolder.getLocale()));
		model.addAttribute(
				"holidayRequest_todate_date_format",
				DateTimeFormat.patternForStyle("S-",
						LocaleContextHolder.getLocale()));
	}

	private String encodeUrlPathSegment(String pathSegment,
			HttpServletRequest request) {
		String enc = request.getCharacterEncoding();
		if (enc == null) {
			enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
		}
		try {
			pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
		} catch (UnsupportedEncodingException uee) {
		}
		return pathSegment;
	}
	
	@RequestMapping(params = "create", method = RequestMethod.GET)
	public String createWithGet(@Valid HolidayRequest holidayRequest,
			BindingResult result, Model model, HttpServletRequest request) {
		return create(holidayRequest, result, model, request);
	}
}

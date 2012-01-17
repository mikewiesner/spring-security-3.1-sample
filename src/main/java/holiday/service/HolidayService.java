package holiday.service;

import holiday.domain.HolidayRequest;

import java.util.List;

public interface HolidayService {

	public void create(HolidayRequest hr);

	public void cancel(HolidayRequest hr);

	public HolidayRequest update(HolidayRequest hr);

	public long countHolidayRequests();

	public List<HolidayRequest> findAllHolidayRequests();

	public HolidayRequest findHolidayRequest(Long id);

	public List<HolidayRequest> findHolidayRequestEntries(int firstResult,
			int maxResults);

}
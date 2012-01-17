package holiday.security;

import holiday.domain.HolidayRequest;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

public class HolidayPermissionEvaluator implements PermissionEvaluator {

	public boolean hasPermission(Authentication auth, Object target, Object perm) {
		if (target instanceof HolidayRequest) {
			HolidayRequest hr = (HolidayRequest) target;
			if (perm.equals("cancel")) {
				return auth.getName().equals(hr.getEmployee());
			}
			else if (perm.equals("show")) {
				if (auth.getName().equals(hr.getEmployee())) {
					hr.setDeletable(true);
				}
				return !hr.getEmployee().equals("rod") || auth.getName().equals("rod");
			}
		}
		throw new UnsupportedOperationException("hasPermission not supported for object <"+target+"> and permission <"+perm+">");
	}

	public boolean hasPermission(Authentication authentication,
			Serializable targetId, String targetType, Object permission) {
		throw new UnsupportedOperationException("Not supported");
	}

}

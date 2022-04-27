package com.uco.myproject.infraestructura.aspecto;

import java.lang.reflect.Method;

import com.uco.myproject.infraestructura.aspecto.exception.ExceptionUserUnauthorized;
import com.uco.myproject.infraestructura.aspecto.service.AuthorizationService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class SecuredResourceAspect {
	
	private static final String MESSAGE_USER_UNATHORIZED = "Forbidden!";
	
	private final AuthorizationService authorizationService;
	
	public SecuredResourceAspect(AuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}

	@Before("@annotation(SecuredResource)")
    public void processMethodsAnnotatedWithProjectSecuredAnnotation(JoinPoint joinPoint) throws Throwable {

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SecuredResource annotation = method.getAnnotation(SecuredResource.class);
       
        String resourceToAuthorized = annotation.name();
        
        boolean isUserAuthorized = this.authorizationService.isAuthorized(resourceToAuthorized);
		
        analyzeIfCanContinue(isUserAuthorized);
    }

	private void analyzeIfCanContinue(boolean isUserAuthorized) throws Throwable {
		if (!isUserAuthorized) {
			throw new ExceptionUserUnauthorized(MESSAGE_USER_UNATHORIZED);
        }
	}
}

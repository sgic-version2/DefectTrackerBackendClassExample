package com.sgic.employee.server.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sgic.common.api.enums.RestApiResponseStatus;
import com.sgic.common.api.response.ApiResponse;
import com.sgic.common.api.response.BasicResponse;
import com.sgic.common.api.response.ValidationFailure;
import com.sgic.employee.dto.mapper.Mapper;
import com.sgic.employee.server.dto.EmployeeDto;
import com.sgic.employee.server.entities.Employee;
import com.sgic.employee.server.services.EmployeeService;
import com.sgic.employee.server.util.Constants;
import com.sgic.employee.server.util.ErrorCodes;
import com.sgic.employee.server.util.ValidationMessages;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	ErrorCodes errorMessages;

	@Autowired
	private Mapper mapper;
	
	  private static final Logger logger = Logger.getLogger(EmployeeController.class);


	@PostMapping(value = "/employee")
	public ResponseEntity<Object> createEmployee(@RequestBody EmployeeDto employeeData) {
		if (employeeService.isEmailAlreadyExist(employeeData.getEmail())) {
		      logger.debug("Email already exists: createEmployee(), email: {}");
		      return new ResponseEntity<>(new BasicResponse<>(
		          new ValidationFailure(Constants.EMAIL, errorMessages.getEmailAlreadyExist()),
		          RestApiResponseStatus.VALIDATION_FAILURE,ValidationMessages.EMAIL_EXIST), HttpStatus.BAD_REQUEST);
		}
		Employee employee = mapper.map(employeeData, Employee.class);
		employeeService.createEmployee(employee);
		return new ResponseEntity<>(new ApiResponse(RestApiResponseStatus.OK), HttpStatus.OK);
		
	}
}

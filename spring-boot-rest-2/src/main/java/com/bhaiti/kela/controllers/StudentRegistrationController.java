package com.bhaiti.kela.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bhaiti.kela.beans.Student;
import com.bhaiti.kela.beans.StudentRegistration;
import com.bhaiti.kela.beans.StudentRegistrationReply;

@Controller
public class StudentRegistrationController {

	@RequestMapping(method = RequestMethod.POST, value = "/register/student")
	
	@ResponseBody
	public StudentRegistrationReply registerStudent(@RequestBody Student student) {
		StudentRegistrationReply registrationReply = new StudentRegistrationReply();
		StudentRegistration.getInstance().add(student);
		registrationReply.setName(student.getName());
		registrationReply.setAge(student.getAge());
		registrationReply.setRegistrationNumber(student.getRegistrationNumber());
		registrationReply.setRegistrationStatus("Successful");
		return registrationReply;
	}
}

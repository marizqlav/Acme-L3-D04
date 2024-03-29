/*
 * AuthenticatedAnnouncementShowService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.student.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.features.rate.ComputeMoneyRate;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentCourseShowService extends AbstractService<Student, Course> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentCourseRepository	repository;

	@Autowired
	protected ComputeMoneyRate			computeMoneyRate;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int courseId;
		Course course;

		courseId = super.getRequest().getData("id", int.class);
		course = this.repository.findOneCourseById(courseId);

		status = course != null && !course.getDraftMode() && super.getRequest().getPrincipal().hasRole(Student.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Course object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCourseById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		Tuple tuple;
		final String systemCurrency = this.repository.findSystemConfiguration().getSystemCurrency();
		final Boolean enrolmented = this.repository.findStudentCourse(super.getRequest().getPrincipal().getActiveRoleId(), object.getId()) != null ? true : false;
		tuple = super.unbind(object, "id", "code", "title", "resumen", "retailPrice", "link");
		tuple.put("lecturerusername", object.getLecturer().getUserAccount().getUsername());
		tuple.put("lectureralmamater", object.getLecturer().getAlmaMater());
		tuple.put("lecturerresume", object.getLecturer().getResume());
		tuple.put("lecturerqualifications", object.getLecturer().getQualifications());
		tuple.put("lecturerlink", object.getLecturer().getLink());
		tuple.put("exchangeMoney", this.computeMoneyRate.computeMoneyExchange(object.getRetailPrice(), systemCurrency).getTarget());
		tuple.put("enrolmented", enrolmented);

		super.getResponse().setData(tuple);

	}

}

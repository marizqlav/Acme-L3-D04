/*
 * AuthenticatedAnnouncementController.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.AssistantService;
import acme.entities.courses.Course;
import acme.entities.practicum.Practicum;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumUpdateService extends AbstractService<Company, Practicum> {

	@Autowired
	protected CompanyPracticumRepository	repository;

	@Autowired
	protected AssistantService				assistantService;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		Practicum practicum;
		Principal principal;
		int practicumId;
		practicumId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findPracticumById(practicumId);
		principal = super.getRequest().getPrincipal();
		status = practicum.getCompany().getId() == principal.getActiveRoleId() && practicum != null && practicum.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Practicum object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findPracticumById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Practicum object) {
		assert object != null;

		super.bind(object, "title", "abstractPracticum", "someGoals");
	}

	@Override
	public void validate(final Practicum object) {
		assert object != null;

		if (object.getTitle() == null || object.getTitle().isEmpty() || object.getTitle() == "")
			super.state(false, "title", "title.not.null");
		if (object.getAbstractPracticum() == null || object.getAbstractPracticum().isEmpty() || object.getAbstractPracticum() == "")
			super.state(false, "abstractPracticum", "abstractPracticum.not.null");
		if (object.getSomeGoals() == null || object.getSomeGoals().isEmpty() || object.getSomeGoals() == "")
			super.state(false, "someGoals", "someGoals.not.null");

	}

	@Override
	public void perform(final Practicum object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;

		Collection<Course> courses;
		SelectChoices choices;
		Tuple tuple;

		courses = this.repository.findAllCourses();
		choices = SelectChoices.from(courses, "code", object.getCourse());

		tuple = super.unbind(object, "title", "abstractPracticum", "someGoals", "draftMode");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}
}

/*
 * AuthenticatedAnnouncementListService.java
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

import acme.entities.practicum.Practicum;
import acme.entities.sessionPracticum.SessionPracticum;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumListService extends AbstractService<Company, Practicum> {

	@Autowired
	protected CompanyPracticumRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Company.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final Collection<Practicum> objects;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		objects = this.repository.findPracticaByCompanyId(principal.getActiveRoleId());

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;
		final Collection<SessionPracticum> sessions;

		sessions = this.repository.findSessionPracticumByPracticumId(object.getId());
		final Double estimatedTimeMenos = object.estimatedTimeMenos(sessions);
		final Double estimatedTimeMas = object.estimatedTimeMas(sessions);

		Tuple tuple;

		tuple = super.unbind(object, "code", "title");
		tuple.put("courseCode", object.getCourse().getCode());
		tuple.put("estimatedTimeMenos", estimatedTimeMenos);
		tuple.put("estimatedTimeMas", estimatedTimeMas);

		super.getResponse().setData(tuple);
	}

}

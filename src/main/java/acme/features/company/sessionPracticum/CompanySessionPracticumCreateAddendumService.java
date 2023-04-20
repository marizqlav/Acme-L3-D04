
package acme.features.company.sessionPracticum;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.sessionPracticum.SessionPracticum;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanySessionPracticumCreateAddendumService extends AbstractService<Company, SessionPracticum> {

	@Autowired
	protected CompanySessionPracticumRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		//		boolean status;
		//
		//		status = super.getRequest().hasData("practicumId", int.class);

		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		//		boolean status;
		//		int practicumingRecordId;
		//		Practicum practicum;
		//
		//		practicumingRecordId = super.getRequest().getData("practicumId", int.class);
		//		practicum = this.repository.findPracticumById(practicumingRecordId);
		//		status = super.getRequest().getPrincipal().hasRole(practicum.getCompany());

		super.getResponse().setAuthorised(true);

	}

	@Override
	public void load() {
		SessionPracticum object;

		object = new SessionPracticum();
		object.setDraftMode(true);
		object.setAddendum(true);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final SessionPracticum object) {
		assert object != null;

		int practicumId;
		Practicum practicum;

		practicumId = super.getRequest().getData("practicumId", int.class);
		practicum = this.repository.findPracticumById(practicumId);

		super.bind(object, "title", "abstractSessionPracticum", "startDate", "finishDate", "link");

		object.setPracticum(practicum);

	}

	@Override
	public void validate(final SessionPracticum object) {
		assert object != null;

		final Date date;

		if (!super.getBuffer().getErrors().hasErrors("finishDate"))
			super.state(object.getStartDate().before(object.getFinishDate()), "finishDate", "company.session-practicum.form.error.finishAfterStart");

		if (!super.getBuffer().getErrors().hasErrors("startDate")) {
			date = CompanySessionPracticumCreateService.oneWeekLong(Date.from(Instant.now()));
			super.state(object.getStartDate().equals(date) || object.getStartDate().after(date), "startDate", "company.session-practicum.form.error.oneWeekAhead");
		}

		if (!super.getBuffer().getErrors().hasErrors("finishDate") && !super.getBuffer().getErrors().hasErrors("startDate")) {
			Date maximumPeriod;
			maximumPeriod = MomentHelper.deltaFromMoment(object.getStartDate(), 7, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfter(object.getFinishDate(), maximumPeriod) && object.getFinishDate().after(object.getStartDate()), "finishDate", "company.session-practicum.form.error.finishDate");
		}

		boolean confirmation;

		confirmation = super.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "company.session-practicum.validation.confirmation");

		//		final Collection<Practicum> practica;
		//		Collection<SessionPracticum> addendums;
		//		final SelectChoices choices;
		//		final int companyId = super.getRequest().getPrincipal().getActiveRoleId();
		//
		//		practica = this.repository.findManyPublishedPracticumByCompanyId(companyId);
		//		choices = SelectChoices.from(practica, "code", object.getPracticum());
		//
		//		final int selectedId = Integer.parseInt(choices.getSelected().getKey());
		//		addendums = this.repository.findAddendumSessionPracticumByPracticumId(selectedId);
		//
		//		final boolean valid = addendums.size() == 0;
		//		super.state(valid, "practicum", "company.session.validation.practicum.error.AddendumAlreadyExists");
	}

	@Override
	public void perform(final SessionPracticum object) {
		assert object != null;
		//		object.setDraftMode(false);
		object.setDraftMode(true);
		this.repository.save(object);
	}

	@Override
	public void unbind(final SessionPracticum object) {
		assert object != null;
		//		final Collection<Practicum> practica;
		//		final SelectChoices choices;
		//		final int companyId = super.getRequest().getPrincipal().getActiveRoleId();

		//		practica = this.repository.findManyPublishedPracticumByCompanyId(companyId);
		//		choices = SelectChoices.from(practica, "code", object.getPracticum());
		Tuple tuple;

		tuple = super.unbind(object, "title", "abstractSessionPracticum", "startDate", "finishDate", "link", "draftMode", "addendum");
		//		tuple.put("practicum", choices.getSelected().getKey());
		//		tuple.put("practica", choices);
		tuple.put("confirmation", false);
		tuple.put("practicumId", super.getRequest().getData("practicumId", int.class));

		super.getResponse().setData(tuple);
		tuple.put("confirmation", false);

	}

	public static Date oneWeekLong(final Date date) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, 7);
		return calendar.getTime();
	}

}

package acme.features.administrator.bulletin;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.bulletin.Bulletin;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBulletinCreateService extends AbstractService<Administrator, Bulletin> {

	@Autowired
	protected AdministratorBulletinRepository repo;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final Bulletin bulletin = new Bulletin();
		Date moment;

		moment = MomentHelper.getCurrentMoment();
		bulletin.setDate(moment);
		super.getBuffer().setData(bulletin);
	}

	@Override
	public void bind(final Bulletin bulletin) {
		assert bulletin != null;

		bulletin.setDate(MomentHelper.getCurrentMoment());

		super.bind(bulletin, "title", "date", "message", "critical", "link");
	}

	@Override
	public void validate(final Bulletin bulletin) {
		assert bulletin != null;

		boolean confirmation;

		confirmation = super.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "javax.validation.constraints.AssertTrue.message");

	}

	@Override
	public void perform(final Bulletin bulletin) {
		assert bulletin != null;
		Date moment;

		moment = MomentHelper.getCurrentMoment();
		bulletin.setDate(moment);
		this.repo.save(bulletin);
	}

	@Override
	public void unbind(final Bulletin bulletin) {
		assert bulletin != null;

		Tuple tuple;

		tuple = super.unbind(bulletin, "title", "date", "message", "critical", "link");
		tuple.put("confirmation", false);
		super.getResponse().setData(tuple);
	}
}

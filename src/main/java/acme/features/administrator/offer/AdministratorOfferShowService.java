
package acme.features.administrator.offer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offer.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorOfferShowService extends AbstractService<Administrator, Offer> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorOfferRepository offerRepository;

	// AbstractService interface ----------------------------------------------ç


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void load() {
		Offer object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.offerRepository.findOfferById(id);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Offer object) {
		assert object != null;

		super.bind(object, "heading", "summary", "availabilityPeriodInitial", "availabilityPeriodFinal", "price", "link");
	}

	@Override
	public void validate(final Offer object) {
		assert object != null;
	}

	@Override
	public void perform(final Offer object) {
		assert object != null;

		this.offerRepository.save(object);
	}

	@Override
	public void unbind(final Offer object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "instantiationMoment", "heading", "summary", "availabilityPeriodInitial", "availabilityPeriodFinal", "price", "link");
		super.getResponse().setData(tuple);
	}
}

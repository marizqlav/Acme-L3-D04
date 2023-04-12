package acme.features.authenticated.audit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.Audit;
import acme.features.auditor.audit.AuditorAuditRepository;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedAuditListByCourseService extends AbstractService<Authenticated, Audit> {

    @Autowired
    AuditorAuditRepository repo;

	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

    @Override
    public void authorise() {
        super.getResponse().setAuthorised(true);
    }

    @Override
    public void load() {
        Integer courseId = super.getRequest().getData("courseId", int.class);

        List<Audit> audits = repo.findAllAuditsByCourse(courseId);

        super.getBuffer().setData(audits);
    }

    @Override
    public void unbind(final Audit audit) {
		assert audit != null;

		Tuple tuple;

		tuple = super.unbind(audit, "code", "conclusion", "strongPoints", "weakPoints");

        super.getResponse().setData(tuple);
    }

}
package acme.features.auditor.auditingRecord;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.AuditingRecord;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordListService extends AbstractService<Auditor, AuditingRecord> {

    @Autowired
    AuditorAuditingRecordRepository repo;

    @Override
    public void check() {
		boolean status;

		status = super.getRequest().hasData("auditId", int.class);

		super.getResponse().setChecked(status);
    }

    @Override
    public void authorise() {
        super.getResponse().setAuthorised(true);
    }

	@Override
	public void load() {
		List<AuditingRecord> auditingRecords = repo.findAllAuditingRecordsFromAudit(super.getRequest().getData("auditId", int.class));

        super.getBuffer().setData(auditingRecords);
    }

    @Override
    public void unbind(final AuditingRecord auditingRecord) {
		assert auditingRecord != null;

		Tuple tuple;

		tuple = super.unbind(auditingRecord, "subject", "assesment", "assesmentStartDate", "assesmentEndDate", "mark");

		if (auditingRecord.isCorrection()) {
			tuple.replace("subject", "* " + tuple.get("subject"));
		}

		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<AuditingRecord> auditingRecords) {
		assert auditingRecords != null;

		boolean draftMode = this.repo.findAudit(super.getRequest().getData("auditId", int.class)).getDraftMode();

		super.getResponse().setGlobal("draftMode", draftMode);
		super.getResponse().setGlobal("auditId", super.getRequest().getData("auditId", int.class));
	}

}

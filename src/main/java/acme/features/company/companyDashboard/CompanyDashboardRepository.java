
package acme.features.company.companyDashboard;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface CompanyDashboardRepository extends AbstractRepository {

	@Query("select c from Company c where c.userAccount.id = ?1")
	Company findCompanyByUserAccountId(int userAccountId);

	@Query("select count(sp) from SessionPracticum sp where sp.practicum.company.id = ?1")
	int findCountSession(int companyId);

	@Query("select COALESCE(avg(datediff(sp.finishDate,sp.startDate)), 0.0) from SessionPracticum sp where sp.practicum.company.id = ?1")
	double findAverageSessionLength(int companyId);

	@Query("select COALESCE(stddev(datediff(sp.finishDate,sp.startDate)), 0.0) from SessionPracticum sp where sp.practicum.company.id = ?1")
	double findDeviationSessionLength(int companyId);

	@Query("select COALESCE(min(datediff(sp.finishDate,sp.startDate)), 0.0) from SessionPracticum sp where sp.practicum.company.id = ?1")
	double findMinimumSessionLength(int companyId);

	@Query("select COALESCE(max(datediff(sp.finishDate,sp.startDate)), 0.0) from SessionPracticum sp where sp.practicum.company.id = ?1")
	double findMaximumSessionLength(int companyId);

	@Query("select COALESCE(avg((select sum(datediff(sp.finishDate,sp.startDate)) from SessionPracticum sp where sp.practicum.company.id = ?1 and sp.practicum.id = p.id)), 0.0) from Practicum p where p.company.id = ?1")
	double findAveragePracticaLength(int companyId);

	@Query("select COALESCE(stddev((select sum(datediff(sp.finishDate,sp.startDate)) from SessionPracticum sp where sp.practicum.company.id = ?1 and sp.practicum.id = p.id)), 0.0) from Practicum p where p.company.id = ?1")
	double findDeviationPracticaLength(int companyId);

	@Query("select COALESCE(min((select sum(datediff(sp.finishDate,sp.startDate)) from SessionPracticum sp where sp.practicum.company.id = ?1 and sp.practicum.id = p.id)), 0.0) from Practicum p where p.company.id = ?1")
	double findMinimumPracticaLength(int companyId);

	@Query("select COALESCE(max((select sum(datediff(sp.finishDate,sp.startDate)) from SessionPracticum sp where sp.practicum.company.id = ?1 and sp.practicum.id = p.id)), 0.0) from Practicum p where p.company.id = ?1")
	double findMaximumPracticaLength(int companyId);

	@Query("select count(p) from Practicum p where p.company.id = ?1")
	int findCountPractica(int companyId);

	@Query("SELECT FUNCTION('MONTH', sp.startDate), COUNT(sp) FROM SessionPracticum sp WHERE sp.practicum.company.id = ?1 GROUP BY FUNCTION('MONTH', sp.startDate) ORDER BY COUNT(sp) DESC")
	List<Object[]> findTotalNumberOfPracticaByMonth(int companyId);
}

package Hrm.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Hrm.model.HrmOrgEntity;


@Repository
public interface HrmOrgRepository extends JpaRepository<HrmOrgEntity, String>{
	List<HrmOrgEntity> findAllByParentIsNull();

	List<HrmOrgEntity> findByCode(String deptCode);

	Optional<HrmOrgEntity> findById(int id);
}

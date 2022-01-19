package Hrm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Hrm.model.HrmOrgEntity;
import Hrm.persistence.HrmOrgRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HrmOrgService {
	@Autowired
	private HrmOrgRepository repository;
	
	public List<HrmOrgEntity> listall() {
		return repository.findAllByParentIsNull();
	}
	
	public List<HrmOrgEntity> listByCode(String deptCode) {
		return repository.findByCode(deptCode);
	}
	
	public Optional<HrmOrgEntity> findById(int Id) {
		return repository.findById(Id);
	}
	
	
	public int deptAdd(final HrmOrgEntity entity) {
		repository.save(entity);

		return entity.getId();
	}
	
	public int deptPut(final HrmOrgEntity entity) {

		final Optional<HrmOrgEntity> original = repository.findById(entity.getId());
		int result=-1;
		if(original.isEmpty()) {
			result = -1;
		}else {
			original.ifPresent(upEntity -> {
				upEntity.setType(entity.getType());
				upEntity.setName(entity.getName());
				upEntity.setCode(entity.getCode());
				upEntity.setManager(entity.getManager());
				upEntity.setParent(entity.getParent());
				repository.save(upEntity);
			});
			result = entity.getId();
		}
		return result;
	}
	
	public int deptDelete(int id) {
		final Optional<HrmOrgEntity> original = repository.findById(id);
		int result=-1;
		if(original.isEmpty()) {
			result = -1;
		}else {
			HrmOrgEntity entity =  original.get();
			repository.delete(entity);
			result = id;
		}
		return result;
	}
	
	public int memberAdd(final HrmOrgEntity entity) {
		repository.save(entity);

		return entity.getId();
	}
	
	public int memberPut(final HrmOrgEntity entity) {

		final Optional<HrmOrgEntity> original = repository.findById(entity.getId());
		int result=-1;
		if(original.isEmpty()) {
			result = -1;
		}else {
			original.ifPresent(upEntity -> {
				upEntity.setType(entity.getType());
				upEntity.setName(entity.getName());
				upEntity.setCode(entity.getCode());
				upEntity.setManager(entity.getManager());
				upEntity.setParent(entity.getParent());
				repository.save(upEntity);
			});
			result = entity.getId();
		}
		return result;
	}
	
	public int memberDelete(int id) {
		final Optional<HrmOrgEntity> original = repository.findById(id);
		int result=-1;
		if(original.isEmpty()) {
			result = -1;
		}else {
			HrmOrgEntity entity =  original.get();
			repository.delete(entity);
			result = id;
		}
		return result;
	}
}

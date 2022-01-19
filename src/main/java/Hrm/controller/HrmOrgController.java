package Hrm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Hrm.dto.HrmOrgDTO;
import Hrm.dto.HrmOrgDeptOnlyDTO;
import Hrm.dto.HrmOrgDeptOnlySchDTO;
import Hrm.dto.HrmOrgMemNotNullDTO;
import Hrm.dto.HrmOrgNotNullDTO;
import Hrm.dto.HrmOrgSchDTO;
import Hrm.dto.HrmSaveDTO;
import Hrm.dto.ResponseDTO;
import Hrm.model.HrmOrgEntity;
import Hrm.service.HrmOrgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/org")
@RequiredArgsConstructor
public class HrmOrgController<T> {
private final HrmOrgService service;
	
	@GetMapping("/organizations")
	public ResponseEntity<?> listall( 
			@RequestParam(required = false) String deptCode,  
		    @RequestParam(required = false) boolean deptOnly,
		    @RequestParam(required = false) String searchType,
		    @RequestParam(required = false) String searchKeyword
		) {
		List<HrmOrgEntity> entities;
		if(deptCode==null) {
			entities =service.listall();
		}else {
			entities =service.listByCode(deptCode);
		}
		
		List<T> dtos = new ArrayList<T>();;
		if(deptOnly){
			if(searchType==null) {
				dtos = (List<T>) entities.stream().map(HrmOrgDeptOnlyDTO::new).collect(Collectors.toList());
			}else {
				if(searchType.equals("dept")) {
					List<HrmOrgDeptOnlySchDTO> reslut = new ArrayList<HrmOrgDeptOnlySchDTO>();
					reslut.add(new HrmOrgDeptOnlySchDTO(entities.get(0),searchKeyword));
					if(reslut.get(0).getId()==null) {
					
					}else {
						dtos.add((T) new HrmOrgNotNullDTO(reslut.get(0)));
					}
				}else if(searchType.equals("member")) {
					List<HrmOrgSchDTO> reslut = new ArrayList<HrmOrgSchDTO>();
					reslut.add(new HrmOrgSchDTO(entities.get(0),searchKeyword));
					if(reslut.get(0).getId()==null) {
						
					}else {
						dtos.add((T) new HrmOrgMemNotNullDTO(reslut.get(0)));
					}
				}
			}
		}else{
			if(searchType==null) {
				dtos = (List<T>) entities.stream().map(HrmOrgDTO::new).collect(Collectors.toList());
			}else {
				if(searchType.equals("dept")) {
					List<HrmOrgDeptOnlySchDTO> reslut = new ArrayList<HrmOrgDeptOnlySchDTO>();
					reslut.add(new HrmOrgDeptOnlySchDTO(entities.get(0),searchKeyword));
					if(reslut.get(0).getId()==null) {
						
					}else {
						dtos.add((T) new HrmOrgNotNullDTO(reslut.get(0)));
					}
				}else if(searchType.equals("member")) {
					List<HrmOrgSchDTO> reslut = new ArrayList<HrmOrgSchDTO>();
					reslut.add(new HrmOrgSchDTO(entities.get(0),searchKeyword));
					if(reslut.get(0).getId()==null) {
						
					}else {
						dtos.add((T) new HrmOrgMemNotNullDTO(reslut.get(0)));
					}
				}
			}
		}
		if(dtos.isEmpty()) {
			ResponseDTO response = ResponseDTO.builder().code("BAD_REQUEST").message("조회된 내역이 없습니다.").build();		    	
	    	return ResponseEntity.badRequest().body(response);
		}else {
			return ResponseEntity.ok().body(dtos);
		}
	}
	
    @PostMapping("/dept")
    public ResponseEntity<?> deptAdd(@RequestBody HrmSaveDTO dto){
    	try {
    		HrmOrgEntity entity = HrmSaveDTO.toEntity(dto);
    		if(dto.getParent()!=0){
    			entity.setParent(service.findById(dto.getParent()).get());
    		}
    		int Id = service.deptAdd(entity);
	    	
	    	
	    	ResponseDTO response = ResponseDTO.builder().code("SAVE SUCCESS").message("ID:"+Id+"를 추가했습니다.").build();
	    	
	    	return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO response = ResponseDTO.builder().code("INTERNAL_SERVER_ERROR").message("내부서버오류").build();
			
			return ResponseEntity.internalServerError().body(response);
		}
    }
    
    @PutMapping("/dept/{id}")
    public ResponseEntity<?> deptPut(@PathVariable int id,@RequestBody HrmSaveDTO dto){
    	try {
    		HrmOrgEntity entity = HrmSaveDTO.toEntity(dto);
    		entity.setParent(service.findById(dto.getParent()).get());
    		entity.setId(id);
    		int Id = service.deptPut(entity);
    		if(Id==-1) {
    			ResponseDTO response = ResponseDTO.builder().code("BAD_REQUEST").message("ID:"+Id+"값과 일치하느 데이터가 없습니다.").build();		    	
		    	return ResponseEntity.badRequest().body(response);
    		}else {
		    	ResponseDTO response = ResponseDTO.builder().code("UPDATE SUCCESS").message("ID:"+Id+"를 업데이트했습니다.").build();		    	
		    	return ResponseEntity.ok().body(response);
    		}
		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO response = ResponseDTO.builder().code("INTERNAL_SERVER_ERROR").message("내부서버오류").build();
			
			return ResponseEntity.internalServerError().body(response);
		}
    }
    
    @DeleteMapping("/dept/{id}")
    public ResponseEntity<?> deptDelete(@PathVariable int id){
    	try {
    		int Id = service.deptDelete(id);
    		if(Id==-1) {
    			ResponseDTO response = ResponseDTO.builder().code("BAD_REQUEST").message("ID:"+Id+"값과 일치하는 데이터가 없습니다.").build();		    	
		    	return ResponseEntity.badRequest().body(response);
    		}else {
		    	ResponseDTO response = ResponseDTO.builder().code("DELETE SUCCESS").message("ID:"+Id+"를 삭제했습니다.").build();		    	
		    	return ResponseEntity.ok().body(response);
    		}
		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO response = ResponseDTO.builder().code("INTERNAL_SERVER_ERROR").message("내부서버오류").build();
			
			return ResponseEntity.internalServerError().body(response);
		}
    }
    
    @PostMapping("/member")
    public ResponseEntity<?> memberAdd(@RequestBody HrmSaveDTO dto){
    	try {
    		HrmOrgEntity entity = HrmSaveDTO.toEntity(dto);
    		entity.setParent(service.findById(dto.getParent()).get());
    		int Id = service.memberAdd(entity);
	    	
	    	
	    	ResponseDTO response = ResponseDTO.builder().code("SAVE SUCCESS").message("ID:"+Id+"를 추가했습니다.").build();
	    	
	    	return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO response = ResponseDTO.builder().code("INTERNAL_SERVER_ERROR").message("내부서버오류").build();
			
			return ResponseEntity.internalServerError().body(response);
		}
    }
    
    @PutMapping("/member/{id}")
    public ResponseEntity<?> memberPut(@PathVariable int id,@RequestBody HrmSaveDTO dto){
    	try {
    		HrmOrgEntity entity = HrmSaveDTO.toEntity(dto);
    		entity.setParent(service.findById(dto.getParent()).get());
    		entity.setId(id);
    		int Id = service.deptPut(entity);
    		if(Id==-1) {
    			ResponseDTO response = ResponseDTO.builder().code("BAD_REQUEST").message("ID:"+Id+"값과 일치하느 데이터가 없습니다.").build();		    	
		    	return ResponseEntity.badRequest().body(response);
    		}else {
		    	ResponseDTO response = ResponseDTO.builder().code("UPDATE SUCCESS").message("ID:"+Id+"를 업데이트했습니다.").build();		    	
		    	return ResponseEntity.ok().body(response);
    		}
		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO response = ResponseDTO.builder().code("INTERNAL_SERVER_ERROR").message("내부서버오류").build();
			
			return ResponseEntity.internalServerError().body(response);
		}
    }
    
    @DeleteMapping("/member/{id}")
    public ResponseEntity<?> memberDelete(@PathVariable int id){
    	try {
    		int Id = service.deptDelete(id);
    		if(Id==-1) {
    			ResponseDTO response = ResponseDTO.builder().code("BAD_REQUEST").message("ID:"+Id+"값과 일치하는 데이터가 없습니다.").build();		    	
		    	return ResponseEntity.badRequest().body(response);
    		}else {
		    	ResponseDTO response = ResponseDTO.builder().code("DELETE SUCCESS").message("ID:"+Id+"를 삭제했습니다.").build();		    	
		    	return ResponseEntity.ok().body(response);
    		}
		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO response = ResponseDTO.builder().code("INTERNAL_SERVER_ERROR").message("내부서버오류").build();
			
			return ResponseEntity.internalServerError().body(response);
		}
    }
}

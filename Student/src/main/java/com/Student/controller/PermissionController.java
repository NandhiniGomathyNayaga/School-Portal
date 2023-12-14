package com.Student.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Student.entity.Permission;
import com.Student.repository.PermissionRepository;
import com.Student.repository.ScreenRepository;

@RestController
@RequestMapping("/permission")
public class ,,PermissionController {
	@Autowired
    private PermissionRepository permissionRepository;
	
	@Autowired
	private ScreenRepository screenRepository;

    @GetMapping("/")
    public List<Permission> getAllPermission() {
        return permissionRepository.findAll();
    }
    @PostMapping("/permissions")
    public Permission createPermission(@RequestBody Permission permission) {
    return permissionRepository.save(permission);
    }
    @GetMapping ("/permissons/{id}")
    public Permission getPermissionById(@PathVariable int id) {
    	Permission permission=permissionRepository.findById(id).orElseThrow(() -> new RuntimeException("Permission was not issued"));
		return permission;
    }
    @PutMapping("/permissions/{id}")
    public ResponseEntity<Permission> updatePermission(@PathVariable int id, @RequestBody Permission permissionDetails){
    	Permission updatepermission=permissionRepository.findById(id).orElseThrow(() -> new RuntimeException("Permission was not issued"));
    	Permission updatedPermission= permissionRepository.save(updatepermission);
        return ResponseEntity.ok (updatedPermission);
    
    }
    @DeleteMapping("/permissions/{id}")
    public String  PermissionDelete(@PathVariable int id) {
    	Permission permission=permissionRepository.findById(id).orElseThrow(() -> new RuntimeException("Permission was not issued"));
        permissionRepository.delete(permission);
        return "Permission was deleted";
    }
  
    
}


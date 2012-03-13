package gov.abrs.etms.service.sys;

import gov.abrs.etms.model.sys.Role;
import gov.abrs.etms.service.util.CrudService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class RoleService extends CrudService<Role> {}

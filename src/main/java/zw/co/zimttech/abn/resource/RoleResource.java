package zw.co.zimttech.abn.resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zw.co.zimttech.abn.entity.Role;
import zw.co.zimttech.abn.resource.generics.GenericResource;
import zw.co.zimttech.abn.service.generics.GenericService;

@RestController
@RequestMapping("/api/role")
public class RoleResource extends GenericResource<Role,String> {
    public RoleResource(GenericService<Role, String> service) {
        super(service);
    }
}

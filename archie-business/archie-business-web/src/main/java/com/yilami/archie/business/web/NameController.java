package com.yilami.archie.business.web;

import com.yilami.archie.business.entity.NameEntity;
import com.yilami.archie.business.service.NameService;
import com.yilami.archie.common.model.KeyValue;
import com.yilami.archie.common.model.Response;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class NameController<T extends NameEntity, S extends NameService<T>> extends BaseController<T, S>{

    public NameController(S service){
        super(service);
    }

    @GetMapping("option/all")
    public Response<List<KeyValue>> findOptions(){
        List<KeyValue> pairs = service.findAll().stream()
                .map(entity -> new KeyValue(entity.getId(), entity.getName())).collect(Collectors.toList());
        return Response.success(pairs);
    }

}

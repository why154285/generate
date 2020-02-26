package ${package_name}.service;
import ${package_name}.model.response.${table_name}Response;
import ${package_name}.model.request.${table_name}Request;
import ${business_package_name}.entity.${table_name};
import ${business_package_name}.dao.${table_name}Repository;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * 描述：${table_annotation} 服务实实现
 * @author ${author}
 * @date ${date}
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class ${table_name}ServiceImpl implements ${table_name}Service{

    @Autowired
    private ${table_name}Repository ${table_name?uncap_first}Repository;
    /**
     * 描述：根据Id获取信息
     * @param id
     */
    @Override
    public ${table_name}Response query${table_name}ById(${id_type} id)throws Exception{
        ${table_name}Response response = new ${table_name}Response();
        BeanUtils.copyProperties(${table_name?uncap_first}Repository.findOne(id),response);
        return response;
    }
    /**
     * 描述：增加信息
     * @param ${table_name?uncap_first}Request
     */
    @Override
    public void add${table_name}(${table_name}Request ${table_name?uncap_first}Request) throws Exception{
        ${table_name} ${table_name?uncap_first} = new ${table_name}();
        BeanUtils.copyProperties(${table_name?uncap_first}Request,${table_name?uncap_first});
        ${table_name?uncap_first}Repository.saveAndFlush(${table_name?uncap_first} );
    }
    /**
     * 描述：根据id删除信息
     * @param id
     */
    @Override
    public void delete${table_name}(${id_type} id) throws Exception{
        ${table_name?uncap_first}Repository.delete(id);
    }
    /**
     * 描述：更新信息
     * @param ${table_name?uncap_first}Request
     */
    @Override
    public void update${table_name}(${table_name}Request ${table_name?uncap_first}Request) throws Exception{
        ${table_name} ${table_name?uncap_first} = new ${table_name}();
        BeanUtils.copyProperties(${table_name?uncap_first}Request,${table_name?uncap_first});
        ${table_name?uncap_first}Repository.saveAndFlush(${table_name?uncap_first} );
    }
    /**
     * 描述：查询所有记录
     */
    @Override
    public List<${table_name}Response> queryAll(){
        List<${table_name}Response> responses = new ArrayList<>();
        Iterator ${table_name}It = ${table_name?uncap_first}Repository.findAll().iterator();
        while (${table_name}It.hasNext()){
            ${table_name}Response response = new ${table_name}Response();
            BeanUtils.copyProperties(${table_name}It.next(),response);
            responses.add(response);
        }
        return responses;
    }

}
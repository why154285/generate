package ${package_name}.service;
import ${package_name}.model.response.${table_name}Response;
import ${package_name}.model.request.${table_name}Request;
import java.util.List;
/**
 * 描述：${table_annotation} 服务实现层接口
 * @author ${author}
 * @date ${date}
 */
public interface ${table_name}Service {

    /**
     * 描述：根据Id获取信息
     * @param id
     */
    ${table_name}Response query${table_name}ById(${id_type} id)throws Exception;
    /**
     * 描述：增加信息
     * @param ${table_name?uncap_first}Request
     */
    void add${table_name}(${table_name}Request ${table_name?uncap_first}Request) throws Exception;
    /**
     * 描述：根据id删除信息
     * @param id
     */
    void delete${table_name}(${id_type} id) throws Exception;
    /**
     * 描述：更新信息
     * @param ${table_name?uncap_first}Request
     */
    void update${table_name}(${table_name}Request ${table_name?uncap_first}Request) throws Exception;
    /**
     * 描述：查询所有记录
     */
    List<${table_name}Response> queryAll();

    }
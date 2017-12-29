### 一、简介  
  + 为客户端封装rest请求，让客户端调用rest接口跟调用本地方法一样
  
#### 客户端使用方式
  + 客户端需要获取的远程数据，由接口提供方提供一个`jar`，然后使用注入的方式，注入需要引用的接口
  + demo
    + 接口提供方提供的接口如下(这个由接口提供方编写，然后提供一个`jar`给客户端使用)：
    
            public interface PersonService {
                  DefaultRpcResult<PersonInfo> personInfo(Integer corpId, Integer personId);
              }
              
    + 客户端使用方式如下：
          
              @Service
              public class SampleServiceImpl implements SampleService {
                  @Autowired
                  private PersonService personService;
                  
                  public PersonInfo sample(Integer corpId, Integer personId){
                      return personService.personInfo(corpId, personId).getValue();
                  }
              
              }
              
#### 服务端使用方式
  + 服务端需要编写接口包，让客户端使用
  + 参数说明：
     + RpcPath : 服务相对路径
     + RpcMethodPath：服务想对路径的子路径
     + RpcResult：所有的返回内容，必须实现RpcResult，且必须是实现类，不能是抽象类或者接口
  
  + 使用方法
     + 所有的GET方法，参数值按照路径占位符的方式进行传递，如：/person/info/corpId/personId
     
            @RpcPath("/person")
            public interface PersonService {
                @RpcMethodPath("/info")
                DefaultRpcResult<PersonInfo> personInfo(Integer corpId, Integer personId);
            }
           
            @Path("person")
            @Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
            @Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
            @Service
            public class PersonRestService implements IPersonRestService {
            
                @GET
                @Path("/info/{corpId : \\d+}/{personId : \\d+}")
                @Override
                public DefaultRpcResult<PersonInfo> personInfo(@PathParam("corpId") Integer corpId, @PathParam("personId") Integer personId) {
                    return personRestService.getPersonInfo(corpId, personId);
                }
            }
     
     
     
   + 所有的POST方法，参数值按照json接收一个对象内容，且只能接收一个对象参数，如：/person/add
        
             @RpcPath("/person")
             
             public interface PersonService {
             
                 @RpcMethodPath("/add")
                 DefaultRpcResult personInfo(PersonInfo personInfo);
             }
            
             @Path("person")
             
             @Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
             
             @Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
             
             @Service
             
             public class PersonRestService implements IPersonRestService {
             
                 @POST
                 @Path("/add")
                 @Override
                 public DefaultRpcResult personInfo(PersonInfo personInfo) {
                     return personRestService.add(personInfo);
                 }
             }
 
#### 翻译组织人事数据
  + 说明：将底层返回的`getData`数据进行翻译，显示出客户端期望的数据(仅仅针对组织人事服务)
  + 约定：由提供方提供客户端需要的字段说明，客户端按照提供方提供的字段，选择渲染本模块的业务数据
          客户端在进行数据组装的时候，必须提供公司ID，和员工ID，否则无法进行数据匹配
  + 提供方示例：提供方需要提供每个前缀对应的请求方法

          public interface PersonConstant {
            /**
             * 员工信息数据前缀
             */
            String PERSON_ALIAS_PREFIX = "person_";
            /**
             * 员工姓名
             */
            String PERSON_NAME = PERSON_ALIAS_PREFIX + "name";
            /**
             * 员工年龄
             */
            String PERSON_AGE = PERSON_ALIAS_PREFIX + "age";
            /**
             * 员工性别
             */
            String PERSON_GENDER = PERSON_ALIAS_PREFIX + "gender";
            }


          public class ServiceLoaderImpl implements RpcConfigProvider {
              private Map<String, Method> urlMapping = new HashMap<>(16);
              {
                  try {
                      urlMapping.put(PersonConstant.PERSON_ALIAS_PREFIX,
                              PersonService.class.getMethod("run", Integer.class, String.class));
                  } catch (NoSuchMethodException e) {
                      e.printStackTrace();
                  }
              }

              private Set<String> personAlias = new HashSet<>(16);
              {
                  personAlias.add(PersonConstant.PERSON_NAME);
                  personAlias.add(PersonConstant.PERSON_AGE);
                  personAlias.add(PersonConstant.PERSON_GENDER);
              }
              @Override
              public String host() {
                  return "http://localhost:8102/";
              }

              @Override
              public String basePackage() {
                  return "com.chen.web.module.hr.org.client.service";
              }

              @Override
              public Map<String, Method> getUrlMapping() {
                  return urlMapping;
              }

              @Override
              public Set<String> getFieldMapping() {
                  return personAlias;
              }
          }
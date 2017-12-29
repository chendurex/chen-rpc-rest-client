import java.io.Serializable;

/**
 * @author chen
 *         2017/11/13 19:00
 */
public class PersonInfo implements Serializable {
    private String name;
    private int age;
    private A a;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class A{
        private String gender;

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }
}

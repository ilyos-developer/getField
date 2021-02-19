import java.lang.reflect.Field;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        try {
            System.out.println("Path is complete      => "+ getField(
                    new Outer(new Inner(new InInner(new AgainInner(new DeepInner(new LastInner("Hello")))))),
                    new String[]{"inner", "inInner", "againInner", "deepInner", "lastInner", "field"}
            ));

            System.out.println("Path is not complete  => "+ getField(
                    new Outer(new Inner(new InInner(new AgainInner(null)))),
                    new String[]{"inner", "inInner", "againInner", "deepInner", "lastInner", "field"}
            ));

            System.out.println("Root is null          => "+ getField(
                    null,
                    new String[]{"inner", "inInner", "againInner", "deepInner", "lastInner", "field"}
            ));

            System.out.println("Target field is null  => "+ getField(
                    new Outer(new Inner(new InInner(new AgainInner(new DeepInner(new LastInner(null)))))),
                    new String[]{"inner", "inInner", "againInner", "deepInner", "lastInner", "field"}
            ));

        } catch (NoSuchFieldException e) {
            System.out.println("Some field's name is not correct");
        } catch (IllegalAccessException e) {
            System.out.println("Some field is not accessible");
        }

    }

    static Optional<Object> getField(Object object, String[] path) throws NoSuchFieldException, IllegalAccessException {
        Field field;

        for (String name : path) {

            if (Optional.ofNullable(object).isEmpty()) return Optional.empty();

            field = object.getClass().getDeclaredField(name);
            field.setAccessible(true);   //for private fields
            object = field.get(object);
        }

        return Optional.ofNullable(object);
    }
}
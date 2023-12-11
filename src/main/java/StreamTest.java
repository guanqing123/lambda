import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author guanqing
 * @description:
 * @since 2023-11-09
 */
public class StreamTest {

    private static List<Author> getAuthors() {
        //数据初始化
        Author author = new Author(1L,"蒙多",33,"一个从菜刀中明悟哲理的祖安人",null);
        Author author2 = new Author(2L,"亚拉索",15,"狂风也追逐不上他的思考速度",null);
        Author author3 = new Author(3L,"易",14,"是这个世界在限制他的思维",null);
        Author author4 = new Author(3L,"易",14,"是这个世界在限制他的思维",null);

        //书籍列表
        List<Book> books1 = new ArrayList<>();
        List<Book> books2 = new ArrayList<>();
        List<Book> books3 = new ArrayList<>();

        books1.add(new Book(1L,"刀的两侧是光明与黑暗","哲学,爱情",88,"用一把刀划分了爱恨"));
        books1.add(new Book(2L,"一个人不能死在同一把刀下","个人成长,爱情",99,"讲述如何从失败中明悟真理"));

        books2.add(new Book(3L,"那风吹不到的地方","哲学",85,"带你用思维去领略世界的尽头"));
        books2.add(new Book(3L,"那风吹不到的地方","哲学",85,"带你用思维去领略世界的尽头"));
        books2.add(new Book(4L,"吹或不吹","爱情,个人传记",56,"一个哲学家的恋爱观注定很难把他所在的时代理解"));

        books3.add(new Book(5L,"你的剑就是我的剑","爱情",56,"无法想象一个武者能对他的伴侣这么的宽容"));
        books3.add(new Book(6L,"风与剑","个人传记",100,"两个哲学家灵魂和肉体的碰撞会激起怎么样的火花呢？"));
        books3.add(new Book(6L,"风与剑","个人传记",100,"两个哲学家灵魂和肉体的碰撞会激起怎么样的火花呢？"));

        author.setBooks(books1);
        author2.setBooks(books2);
        author3.setBooks(books3);
        author4.setBooks(books3);

        List<Author> authorList = new ArrayList<>(Arrays.asList(author,author2,author3,author4));
        return authorList;
    }

    private static Author getAuthor() {
        //数据初始化
        Author author = new Author(1L,"蒙多",33,"一个从菜刀中明悟哲理的祖安人",null);
        return author;
    }

    public static void main(String[] args) {
        List<Author> authors = getAuthors();
        Stream<Author> authorStream = authors.stream();
        authorStream.filter(new Predicate<Author>() {
            @Override
            public boolean test(Author author) {
                return author.getAge()>17;
            }
        }.and(new Predicate<Author>() {
            @Override
            public boolean test(Author author) {
                return author.getName().length()>1;
            }
        })).forEach(author -> System.out.println(author.getName()));
    }

    private static void map() {
        Optional<Author> authorOptional = Optional.ofNullable(getAuthor());
        Optional<List<Book>> books = authorOptional.map(author -> author.getBooks());
        books.ifPresent(books1 -> books1.forEach(book -> System.out.println(book.getName())));
    }

    private static void isPresent() {
        Optional<Author> authorOptional = Optional.ofNullable(getAuthor());
        if (authorOptional.isPresent()){
            System.out.println(authorOptional.get().getName());
        }
    }

    private static void filter() {
        Optional<Author> authorOptional = Optional.ofNullable(getAuthor());
        authorOptional.filter(author -> author.getAge()>100)
                .ifPresent(author -> System.out.println(author.getName()));
    }

    private static void orElseThrow() {
        Optional<Author> authorOptional = Optional.ofNullable(getAuthor());
        try {
            Author author = authorOptional.orElseThrow(() -> new RuntimeException("author为空"));
            System.out.println(author.getName());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private static void orElseGet() {
        Optional<Author> authorOptional = Optional.ofNullable(getAuthor());
        Author author = authorOptional.orElseGet(() -> new Author());
    }


    private static void OptionalTest() {
        List<Author> authors = getAuthors();
        Optional<List<Author>> authorOptional = Optional.ofNullable(authors);
    }

    private static void reduce4() {
        List<Author> authors = getAuthors();
        Optional<Integer> reduce = authors.stream()
                .map(author -> author.getAge())
                .reduce((result, element) -> result < element ? result : element);
        reduce.ifPresent(integer -> System.out.println(integer));
    }

    private static void reduce3() {
        List<Author> authors = getAuthors();
        Integer min = authors.stream()
                .map(author -> author.getAge())
                .reduce(Integer.MAX_VALUE, (result, element) -> result < element ? result : element);
        System.out.println(min);
    }

    private static void reduce2() {
        List<Author> authors = getAuthors();
        Integer max = authors.stream()
                .map(author -> author.getAge())
                .reduce(Integer.MIN_VALUE, (result, element) -> result > element ? result : element);
        System.out.println(max);
    }

    private static void reduce1() {
        List<Author> authors = getAuthors();
        Integer reduce = authors.stream()
                .map(author -> author.getAge())
                .reduce(0, (result, element) -> result + element);
        System.out.println(reduce);
    }

    private static void findFirst() {
        List<Author> authors = getAuthors();
        Optional<Author> first = authors.stream()
                .sorted((o1, o2) -> o1.getAge() - o2.getAge())
                .findFirst();
        first.ifPresent(author -> System.out.println(author.getName() + "\t" + author.getAge()));
    }

    private static void findAny() {
        List<Author> authors = getAuthors();
        Optional<Author> any = authors.stream()
                .filter(author -> author.getAge() > 18)
                .findAny();
        any.ifPresent(author -> System.out.println(author.getName()));
    }

    private static void noneMatch() {
        List<Author> authors = getAuthors();
        boolean b = authors.stream()
                .noneMatch(author -> author.getAge() > 100);
        System.out.println(b);
    }

    private static void allMatch() {
        List<Author> authors = getAuthors();
        boolean b = authors.stream()
                .allMatch(author -> author.getAge() > 18);
        System.out.println(b);
    }

    private static void anyMatch() {
        List<Author> authors = getAuthors();
        boolean b = authors.stream()
                .anyMatch(author -> author.getAge() > 29);
        System.out.println(b);
    }

    private static void toList() {
        List<Author> authors = getAuthors();
        List<String> names = authors.stream()
                .map(author -> author.getName())
                .collect(Collectors.toList());
        Set<String> books = authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .map(book -> book.getName())
                .collect(Collectors.toSet());
        List<Map> collect = authors.stream()
                .map(author -> {
                    Map map = new HashMap();
                    map.put(author.getName(), author.getBooks());
                    return map;
                })
                .collect(Collectors.toList());
        Map<String, List<Book>> map = authors.stream()
                .distinct()
                .collect(Collectors.toMap(author -> author.getName(), author -> author.getBooks()));
        System.out.println(books);
        System.out.println(collect);
        System.out.println(map);
    }

    private static void maxin() {
        List<Author> authors = getAuthors();
        // stream<Author> -> stream<Book> -> stream<Integer> 求值
        Optional<Integer> max = authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .map(book -> book.getScore())
                .max((score1, score2) -> score1 - score2);
        Optional<Integer> min = authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .map(book -> book.getScore())
                .min((score1, score2) -> score1 - score2);
        System.out.println(max.get());
        System.out.println(min.get());
    }

    private static void countStream() {
        List<Author> authors = getAuthors();
        long count = authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .distinct()
                .count();
        System.out.println(count);
    }

    private static void flatMap2() {
        List<Author> authors = getAuthors();
        authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .distinct()
                .flatMap(book -> Arrays.stream(book.getCategory().split(",")))
                .distinct()
                .forEach(s -> System.out.println(s));
    }

    private static void flatMap() {
        List<Author> authors = getAuthors();
        authors.stream()
                .flatMap(new Function<Author, Stream<Book>>() {
                    @Override
                    public Stream<Book> apply(Author author) {
                        return author.getBooks().stream();
                    }
                })
                .distinct()
                .forEach(book -> System.out.println(book.getName()));
    }

    private static void skipStream() {
        List<Author> authors = getAuthors();
        authors.stream()
                .distinct()
                .sorted((o1, o2) -> o2.getAge() - o1.getAge())
                .skip(1)
                .forEach(author -> System.out.println(author.getName() +"\t" + author.getAge()));
    }

    private static void limitStream() {
        List<Author> authors = getAuthors();
        authors.stream()
                .distinct()
                .sorted((o1, o2) -> o2.getAge() - o1.getAge())
                .limit(2)
                .forEach(author -> System.out.println(author.getName() + "\t" + author.getAge()));
    }

    private static void distinctStream() {
        List<Author> authors = getAuthors();
        authors.stream()
                .distinct()
                .forEach(author -> System.out.println(author.getName()));
    }

    private static void mapStream2() {
        List<Author> authors = getAuthors();
        authors.stream()
                .map(author -> author.getAge())
                .map(age -> age + 10)
                .forEach(integer -> System.out.println(integer));
    }

    private static void mapStream() {
        Map<String, Integer> map = new HashMap<>();
        map.put("蜡笔小新",19);
        map.put("黑子",17);
        map.put("日向翔阳", 16);

        Stream<Map.Entry<String, Integer>> stream = map.entrySet().stream();
        stream.filter(entry -> entry.getValue()>16).
                forEach(entry -> System.out.println(entry.getKey() + "\t" + entry.getValue()));
    }

    private static void listSteam() {
        List<Author> authors = getAuthors();
        authors.stream()
                .distinct()
                .filter(author -> author.getAge()<18)
                .forEach(author -> System.out.println(author.getName()));
    }
}

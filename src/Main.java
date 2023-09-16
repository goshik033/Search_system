

/*
-- ПРИНЦИП РАБОТЫ --
Сначала проходимся по всем документам и запонляем HashMap, где ключем является слово,
а значением HashMap с номером документа и количеством слов.
После этого проходимся по каждому уникальному слову запроса и проверяем есть ли оно в HashMap cо словами из документов,
если есть, то добавляем в Списко, в котором хронятся массивы,
в которых хронятся номер количество вхождений слова и номер строки
Сортируем список по значениям и выводим пять первых элементов, если они есть и не равны 0 для каждого запроса

-- ДОКАЗАТЕЛЬСТВО КОРРЕКТНОСТИ --
Когда мы проходимся циклом по запросам и считываем уникальные слова в каждой строке,
мы начинаем искать эти слова в Map со словами из докумментов, и если находим,
то проверяем, если ли строка в Map, в которой подсчитываются символы, если есть, то увеличиваем , если нет,
то добавляем новую строку
Изначально список с количеством вхождений слова из запроса в документы отсортирован по номерам этих документов,
но после этого мы устойчиво сортируем ее по значениям, и поэтому мы можем просто вывести пять первых элементов

-- ВРЕМЕННАЯ СЛОЖНОСТЬ --
Первый этап заполнение HasMap словами и их количествами в документах :
Средняя сложность будет O(n*lenn)
где n- кол-во документов, lenn - количество слов документе
Так как вставка и поиск в HashMap выполняются за O(1)

Второй этап Заполнение Set уникальными словами из запросов и нахождение ответа:
Сложность перебера всех запросов будет O(m*lenm)
где m- кол-во запросов, lenm - количество слов в запросе
И сложность сортировки запросов O(n)
где n- кол-во документов

Общая сложность будет O(n * lenn + m * lenm * n )

-- ПРОСТРАНСТВЕННАЯ СЛОЖНОСТЬ --

В худшем случае, когда все слова уникальны в первой HashMap, у которой ключ - слово,
а значение - еще одна HashMap c номером строки и количеством,s
пространственная сложность может составить O(n * lenn),
где n - количество строк, а lenn - количество слов в строках.
Во второй HashMap counter сложность будет O(m)


общая пространственная сложность алгоритма будет примерно O(n * len + m )

Id 90009591
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(bf.readLine());
        HashMap<String, LinkedHashMap<Integer, Integer>> list = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            String[] arr = bf.readLine().split(" ");
            for (int j = 0; j < arr.length; j++) {
                if (list.containsKey(arr[j])) {
                    if (list.get(arr[j]).containsKey(i)) {
                        list.get(arr[j]).put(i, list.get(arr[j]).get(i) + 1);
                    } else {
                        list.get(arr[j]).put(i, 1);
                    }
                } else {
                    LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>();
                    map.put(i, 1);
                    list.put(arr[j], map);
                }
            }
        }
        int m = Integer.parseInt(bf.readLine());
        for (int i = 0; i < m; i++) {
            Set<String> map = Stream.of(bf.readLine().split("\\s+"))
                    .collect(Collectors.toCollection(LinkedHashSet::new));
            HashMap<Integer, Integer> counter = new HashMap<>();
            for (String s : map) {
                if (list.containsKey(s)) {
                    for (Integer integer : list.get(s).keySet()) {
                        if (counter.containsKey(integer)) {
                            counter.put(integer, counter.get(integer) + list.get(s).get(integer));
                        } else {
                            counter.put(integer, list.get(s).get(integer));
                        }
                    }
                }
            }
            List<Map.Entry<Integer, Integer>> slist = new ArrayList<>(counter.entrySet());
            for (int iteration = 0; iteration < 5; iteration++) {
                if (counter.size() > 0) {
                    int maxx = Integer.MIN_VALUE;
                    int maxxNum = Integer.MAX_VALUE;
                    int ind = -1;
                    for (Map.Entry<Integer, Integer> entry : slist) {
                        if (entry.getValue() > maxx) {
                            maxx = entry.getValue();
                            maxxNum = entry.getKey();
                            ind = slist.indexOf(entry);
                        } else if (entry.getValue() == maxx) {
                            if (entry.getKey() < maxxNum) {
                                maxxNum = entry.getKey();
                                ind = slist.indexOf(entry);
                            }
                        }
                    }
                    if (maxxNum != Integer.MAX_VALUE) {
                        counter.remove(maxxNum);
                        sb.append(maxxNum + 1 + " ");
                        slist.remove(ind);
                    }

                }
            }
            sb.append("\n");

        }
        System.out.println(sb);
    }
}
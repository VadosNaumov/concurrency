package course.concurrency.m3_shared.collections;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

public class RestaurantService {

    private Map<String, Restaurant> restaurantMap = new ConcurrentHashMap<>() {{
        put("A", new Restaurant("A"));
        put("B", new Restaurant("B"));
        put("C", new Restaurant("C"));
    }};

    public Restaurant getByName(String restaurantName) {
        addToStat(restaurantName);
        return restaurantMap.get(restaurantName);
    }

    private final ConcurrentHashMap<String, LongAdder> stat = new ConcurrentHashMap<>();

    public void addToStat(String restaurantName) {
        stat.computeIfAbsent(restaurantName, rn -> new LongAdder()).increment();
    }

    public Set<String> printStat() {
        Set<String> result = new HashSet<>();
        for (Map.Entry<String, LongAdder> entry : stat.entrySet()) {
            result.add(entry.getKey() + " - " + entry.getValue());
        }
        return result;
    }
}

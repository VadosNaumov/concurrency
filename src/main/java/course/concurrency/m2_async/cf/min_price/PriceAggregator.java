package course.concurrency.m2_async.cf.min_price;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.*;

public class PriceAggregator {

    private PriceRetriever priceRetriever = new PriceRetriever();

    public void setPriceRetriever(PriceRetriever priceRetriever) {
        this.priceRetriever = priceRetriever;
    }

    private Collection<Long> shopIds = Set.of(10l, 45l, 66l, 345l, 234l, 333l, 67l, 123l, 768l);

    public void setShops(Collection<Long> shopIds) {
        this.shopIds = shopIds;
    }

    public double getMinPrice(long itemId) {
        ExecutorService executor = Executors.newCachedThreadPool();

        CompletableFuture<Double>[] priceFutures = shopIds.stream()
                .map(shopId -> CompletableFuture.supplyAsync(() -> priceRetriever.getPrice(itemId, shopId), executor))
                .toArray(CompletableFuture[]::new);
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(priceFutures);

        try {
            allFutures.get(2950, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        return Arrays.stream(priceFutures)
                .mapToDouble(future -> {
                    try {
                        return future.getNow(Double.NaN);
                    } catch (Exception e) {
                        return Double.NaN;
                    }
                })
                .filter(price -> !Double.isNaN(price))
                .min()
                .orElse(Double.NaN);
    }
}

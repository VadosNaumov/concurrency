package course.concurrency.exams.auction;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class AuctionStoppableOptimistic implements AuctionStoppable {

    private Notifier notifier;

    public AuctionStoppableOptimistic(Notifier notifier) {
        this.notifier = notifier;
        this.latestBid = new AtomicMarkableReference<>(new Bid(-1L, -1L, -1L), false);
    }

    private AtomicMarkableReference<Bid> latestBid;

    public boolean propose(Bid bid) {
        Bid currentBid;
        do {
            currentBid = latestBid.getReference();
            if (bid.getPrice() <= currentBid.getPrice()) {
                return false;
            }
        } while (!latestBid.compareAndSet(currentBid, bid, false, false));
        notifier.sendOutdatedMessage(currentBid);
        return true;
    }

    public Bid getLatestBid() {
        return latestBid.getReference();
    }

    public Bid stopAuction() {
        notifier.shutdown();
        Bid b = latestBid.getReference();
        latestBid.compareAndSet(b, b, false, true);
        return latestBid.getReference();
    }
}

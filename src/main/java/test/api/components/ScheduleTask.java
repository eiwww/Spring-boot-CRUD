package test.api.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import test.api.repositories.RefreshTokenRepo;

@Component
public class ScheduleTask {

    @Autowired
    private RefreshTokenRepo refreshTokenRepo;

    // for check cron expression go https://crontab.cronhub.io/
    // @Scheduled(cron="*/10 * * * * *")
    // public void testSchedule() {
    //     System.out.println("Schedule task running " + LocalDateTime.now());
    // }

    @Scheduled(cron = "0 */10 * * * *") // every 10 minutes
    public void checkRefreshTokenExpired() {
        int deleteCount = refreshTokenRepo.deleteAllExpiredTokens();
        System.out.println("Delete expiry refreshToken count = " + deleteCount);
    }
}

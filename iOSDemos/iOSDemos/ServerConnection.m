//
//  ServerConnection.m
//  MyBlogAsyncTaskSeriesDemos
//
//  Created by Charles Zhang on 3/27/16.
//  Copyright © 2016 tielei. All rights reserved.
//

#import "ServerConnection.h"
#import "Reachability.h"

@interface ServerConnection() {
    //用户执行socket操作的GCD queue
    dispatch_queue_t socketQueue;
    Reachability *reachability;
}
@end

@implementation ServerConnection

- (instancetype)init {
    self = [super init];
    if (self) {
        socketQueue = dispatch_queue_create("SocketQueue", NULL);
        
        reachability = [[Reachability alloc] init];
        [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(networkStateChanged:) name:kReachabilityChangedNotification object:reachability];
        [reachability startNetworkMonitoring];
        NSLog(@"ServerConnection init, start network monitoring...");
    }
    return self;
}

- (void)dealloc {
    [reachability stopNetworkMonitoring];
    [[NSNotificationCenter defaultCenter] removeObserver:self];
    NSLog(@"ServerConnection dealloc, stop network monitoring... isMainThread? %d", (dispatch_get_current_queue() == dispatch_get_main_queue()));
}


- (void)networkStateChanged:(NSNotification *)notification {
    NetworkStatus networkStatus = [notification.userInfo[networkStatusNotificationInfoKey] unsignedIntValue];
    if (networkStatus != NotReachable) {
        //网络变化，重连
        NSLog(@"networkStateChanged detected");
        dispatch_async(socketQueue, ^{
            [self reconnect];
        });
    }
}


- (void)reconnect {
    //TODO:
    NSLog(@"reconnecting... isMainThread? %d", (dispatch_get_current_queue() == dispatch_get_main_queue()));
    sleep(5);
}



@end

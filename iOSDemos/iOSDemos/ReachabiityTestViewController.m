//
//  MyViewController.m
//  MyBlogAsyncTaskSeriesDemos
//
//  Created by Charles Zhang on 3/27/16.
//  Copyright © 2016 tielei. All rights reserved.
//

#import "ReachabiityTestViewController.h"
#import "ServerConnection.h"

@interface ReachabiityTestViewController () {
    ServerConnection *serverConnection;
}

@end

@implementation ReachabiityTestViewController


- (void)awakeFromNib {
    [super awakeFromNib];
    
    serverConnection = [[ServerConnection alloc] init];
}

@end

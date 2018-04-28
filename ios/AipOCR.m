//
//  ViewController.m
//  AipOcrDemo
//
//  Created by xuyazhong on 18/1/8.
//  Copyright © 2018年 xyz. All rights reserved.
//

#import "AipOCR.h"
#import <objc/runtime.h>
#import <AipOcrSdk/AipOcrSdk.h>
#import "YYModel.h"

@implementation AipOCR

RCT_EXPORT_MODULE()

- (instancetype)init {
  self = [super init];
  if (self) {
    NSString *licenseFile = [[NSBundle mainBundle] pathForResource:@"aip" ofType:@"license"];
    NSData *licenseFileData = [NSData dataWithContentsOfFile:licenseFile];
    if(!licenseFileData) {
      [[[UIAlertView alloc] initWithTitle:@"授权失败" message:@"授权文件不存在" delegate:nil cancelButtonTitle:@"确定" otherButtonTitles:nil] show];
    }
    [[AipOcrService shardService] authWithLicenseFileData:licenseFileData];
    
  }
  return self;
}

- (UIViewController *)getRootVC {
  UIViewController *root = [[[[UIApplication sharedApplication] delegate] window] rootViewController];
  while (root.presentedViewController != nil) {
    root = root.presentedViewController;
  }
  return root;
}

- (NSString *)FailureResult:(NSError *)error {
  NSLog(@"%@", error);
  NSString *msg = [NSString stringWithFormat:@"%li:%@", (long)[error code], [error localizedDescription]];
  return msg;
}

- (NSString *)FrontResult:(id)result {
  NSLog(@"result: %@", result);
    NSDictionary *ocr = result[@"words_result"];
    if([ocr isKindOfClass:[NSDictionary class]]){
      OCRModel *front = [[OCRModel alloc] init];
      front.idNumber = ocr[@"公民身份号码"][@"words"];
      front.name = ocr[@"姓名"][@"words"];
      front.gender = ocr[@"性别"][@"words"];
      front.ethnic = ocr[@"民族"][@"words"];
      front.birthday = ocr[@"出生"][@"words"];
      front.address = ocr[@"住址"][@"words"];
      NSString *res = [front yy_modelToJSONString];
      NSLog(@"res = [%@]", res);
      return res;
    } else {
      return @"error";
    }
  
}


RCT_EXPORT_METHOD(actionIDCardOCRFront:(RCTResponseSenderBlock)callback) {
  
    UIViewController *vc = [AipCaptureCardVC ViewControllerWithCardType:CardTypeIdCardFont andImageHandler:^(UIImage *image) {
      [self detectIDCardFront:image callback:callback];
    }];
  
  self.block = ^{
    [vc dismissViewControllerAnimated:YES completion:nil];
  };
  
    dispatch_async(dispatch_get_main_queue(), ^{
  
      [[self getRootVC] presentViewController:vc animated:YES completion:nil];
      
    });
}

- (void)detectIDCardFront:(UIImage *)image
                 callback:(RCTResponseSenderBlock)callback {
  [[AipOcrService shardService] detectIdCardFrontFromImage:image withOptions:nil successHandler:^(id result) {
    NSLog(@"result: %@", result);
    callback(@[[NSNull null], [self FrontResult:result]]);
    self.block();
  } failHandler:^(NSError *err) {
    callback(@[err.userInfo, [NSNull null]]);
    self.block();
  }];
  
}


+ (BOOL)requiresMainQueueSetup {
  return YES;
}

@end

@implementation OCRModel

@end


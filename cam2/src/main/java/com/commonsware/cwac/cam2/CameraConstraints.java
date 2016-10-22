/***
 Copyright (c) 2016 CommonsWare, LLC

 Licensed under the Apache License, Version 2.0 (the "License"); you may
 not use this file except in compliance with the License. You may obtain
 a copy of the License at http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package com.commonsware.cwac.cam2;

import android.media.CamcorderProfile;
import android.os.Build;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class CameraConstraints {
  private static final ArrayList<CameraConstraints> CONSTRAINTS=
    new ArrayList<>();
  private static final CameraConstraints DEVICE_CONSTRAINT;
  private final Pattern manufacturer;
  private final Pattern product;
  private final Pattern model;
  private final boolean supportsCameraTwo;
  private final int highCamcorderProfile;
  private final boolean disableFocusMode;
  private final int cameraDisplayOrientation;
  private final boolean supportsFFC;
  private final boolean supportsRFC;

  static {
    add(new Builder()
      .manufacturer("htc")
      .product("volantis")
      .disableFocusMode(true)
      .build());
    add(new Builder()
      .manufacturer("htc")
      .product("volantisg")
      .disableFocusMode(true)
      .build());
    add(new Builder()
      .manufacturer("HUAWEI")
      .product("KIW-L24")
      .supportsCameraTwo(false)
      .highCamcorderProfile(CamcorderProfile.QUALITY_1080P)
      .build());
    add(new Builder()
      .manufacturer("LGE")
      .product("hammerhead")
      .supportsCameraTwo(false)
      .build());
    add(new Builder()
      .manufacturer("LGE")
      .product("bullhead")
      .supportsCameraTwo(false)
      .build());
    add(new Builder()
      .manufacturer("LGE")
      .product("g3_tmo_us")
      .highCamcorderProfile(CamcorderProfile.QUALITY_480P)
      .build());
    add(new Builder()
      .manufacturer("LGE")
      .product("palman")
      .supportsCameraTwo(false)
      .build());
    add(new Builder()
      .manufacturer("NVIDIA")
      .product("sb_na_wf")
      .supportsCameraTwo(false)
      .build());
    add(new Builder()
      .manufacturer("samsung")
      .product("ha3gub")
      .supportsCameraTwo(false)
      .build());
    add(new Builder()
      .manufacturer("samsung")
      .product("mantaray")
      .supportsCameraTwo(false)
      .build());
    add(new Builder()
      .manufacturer("samsung")
      .product("sf2wifixx")
      .cameraDisplayOrientation(0)
      .build());
    add(new Builder()
      .manufacturer("Sony")
      .product("C6603")
      .disableFocusMode(true)
      .build());
    add(new Builder()
      .manufacturer("Sony")
      .product("C6802")
      .disableFocusMode(true)
      .build());
    add(new Builder()
      .manufacturer("Sony")
      .product("D5803")
      .disableFocusMode(true)
      .build());

    CameraConstraints match=null;

    for (CameraConstraints m : CONSTRAINTS) {
      if (m.isMatch()) {
        match=m;
        break;
      }
    }

    DEVICE_CONSTRAINT=match;
  }

  public static void add(CameraConstraints matcher) {
    CONSTRAINTS.add(matcher);
  }

  public static CameraConstraints get() {
    return(DEVICE_CONSTRAINT);
  }

  private CameraConstraints(Pattern manufacturer,
                            Pattern product, Pattern model,
                            boolean supportsCameraTwo,
                            int highCamcorderProfile,
                            boolean disableFocusMode,
                            int cameraDisplayOrientation,
                            boolean supportsFFC,
                            boolean supportsRFC) {
    this.manufacturer=manufacturer;
    this.product=product;
    this.model=model;
    this.supportsCameraTwo=supportsCameraTwo;
    this.highCamcorderProfile=highCamcorderProfile;
    this.disableFocusMode=disableFocusMode;
    this.cameraDisplayOrientation=cameraDisplayOrientation;
    this.supportsFFC=supportsFFC;
    this.supportsRFC=supportsRFC;
  }

  public boolean isMatch() {
    boolean result=true;

    if (manufacturer!=null) {
      result=manufacturer.matcher(Build.MANUFACTURER).matches();
    }

    if (result && product!=null) {
      result=product.matcher(Build.PRODUCT).matches();
    }

    if (result && model!=null) {
      result=model.matcher(Build.MODEL).matches();
    }

    return(result);
  }

  public boolean supportsCameraTwo() {
    return(supportsCameraTwo);
  }

  public int getHighCamcorderProfile() {
    return(highCamcorderProfile);
  }

  public boolean getDisableFocusMode() {
    return(disableFocusMode);
  }

  public int getCameraDisplayOrientation() {
    return(cameraDisplayOrientation);
  }

  public boolean supportsFFC() {
    return(supportsFFC);
  }

  public boolean supportsRFC() {
    return(supportsRFC);
  }

  public static class Builder {
    private Pattern manufacturer;
    private Pattern product;
    private Pattern model;
    private boolean supportsCameraTwo=true;
    private int highCamcorderProfile=CamcorderProfile.QUALITY_HIGH;
    private boolean disableFocusMode=false;
    private int cameraDisplayOrientation=-1;
    private boolean supportsFFC=true;
    private boolean supportsRFC=true;

    public Builder manufacturer(String mfr) {
      return(manufacturer(Pattern.compile(mfr)));
    }

    public Builder manufacturer(Pattern manufacturer) {
      this.manufacturer=manufacturer;

      return(this);
    }

    public Builder product(String product) {
      return(product(Pattern.compile(product)));
    }

    public Builder product(Pattern product) {
      this.product=product;

      return(this);
    }

    public Builder model(String model) {
      return(model(Pattern.compile(model)));
    }

    public Builder model(Pattern model) {
      this.model=model;

      return(this);
    }

    public Builder supportsCameraTwo(boolean supportsCameraTwo) {
      this.supportsCameraTwo=supportsCameraTwo;

      return(this);
    }

    public Builder highCamcorderProfile(int highCamcorderProfile) {
      this.highCamcorderProfile=highCamcorderProfile;

      return(this);
    }

    public Builder disableFocusMode(boolean disableFocusMode) {
      this.disableFocusMode=disableFocusMode;

      return(this);
    }

    public Builder cameraDisplayOrientation(int cameraDisplayOrientation) {
      this.cameraDisplayOrientation=cameraDisplayOrientation;

      return(this);
    }

    public Builder supportsFFC(boolean supportsFFC) {
      this.supportsFFC=supportsFFC;

      return(this);
    }

    public Builder supportsRFC(boolean supportsRFC) {
      this.supportsRFC=supportsRFC;

      return(this);
    }

    public CameraConstraints build() {
      return(new CameraConstraints(manufacturer, product, model,
        supportsCameraTwo, highCamcorderProfile, disableFocusMode,
        cameraDisplayOrientation, supportsFFC, supportsRFC));
    }
  }
}

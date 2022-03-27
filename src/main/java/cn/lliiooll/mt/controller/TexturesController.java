package cn.lliiooll.mt.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.lliiooll.mt.beans.YggdrasilResp;
import cn.lliiooll.mt.beans.yggdrasil.*;
import cn.lliiooll.mt.pojo.Profile;
import cn.lliiooll.mt.pojo.ProfileTextures;
import cn.lliiooll.mt.pojo.UserRole;
import cn.lliiooll.mt.service.ProfileService;
import cn.lliiooll.mt.service.TexturesService;
import cn.lliiooll.mt.service.UserService;
import cn.lliiooll.mt.utils.ObjectUtils;
import cn.lliiooll.mt.utils.SpringUtils;
import cn.lliiooll.mt.utils.YggdrasilUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
public class TexturesController {

    @RequestMapping(value = "/textures/{hash}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] textures(@PathVariable("hash") String hash) {
        return Base64.decode("iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAMAAACdt4HsAAAAyVBMVEUAAAA0NDM+Pj1FRURHSktOTk1WVlVWVlZXW1xdSzddVE5eTDhfY2RmZmZnUz1nXVVscXJwWkJ1dXV4fX56gIB+ZUqCgoKHjo+McVOafFuoh2OsrKyvr6+yjXa2h2m8kXbCmoHCm4LHo43IpY/NrZjOr5vTs4zUs4zUuajYtJbgvZTgvpThvpTlv5/m5ubow5vrtZTrvKDrw8vtyJztyZzux5zuzaXyyqj19fX6zKT6zaT60aT606T71KT72K7+2Ln+3sL/1bH/16fbbdqIAAAAAXRSTlMAQObYZgAAA8dJREFUWMPtVm1D0zAQ3gQFJI6Utqyi9hq6KeAQdK0VSeva//+jvLukb7AxCn701iTXLPc0d7kkz2hkJQQIbSEJR0MFYojxgVqGA8zVHB+ASM1jFQ0HUOcqnmOl5gQwV8MB5io+x0rFl3fLy1g9x4WYXbi8Q7mE5wdxeZfnd8vhAM0y3izzYnkTDjcMgZ9OPnDpj3xk6qGiVoWNK7Ydjcd2IMzjOj3qvl7wYhqAQ8wyNu1o58eb9kMqJt129ZcP8McVJgPlQd2Oxjs7FkDh2jKA7eonEBwdLb4cHQGq+GVMKNvW4+LYTHhjCr/9dlJmJ9/eNilt2wZg6/r/+vDn54df8CCI9Uy3LSN8T/8U6XfYuK0fywzH8X3HmUyaWUGEAuDQH/hP3Z+iCJSHAH7wHu1bgCg6O0MENA+CwG8Brq9TobONAAf1O9ovFoiA/aenDwD0OgA/6LoQnUGSwFnkOEFwGmxxgd0nV7EhjQZFM8hzmEWkh5g/mGFhypJbSdMGYJEki897SfIVJbm6ukirKkXbAlEi1lWMh4yCvCjStCjy3JQWINE6Sd7dJtxmWUJThJnAuc6AdZPWwB9nB0jyFkBnmS6Pb7UuS51kZSakFGImUQSwDgQA1lDyH7046Kys9PFK69VK/0YQKT3x6dPY88ahEB7BkAsgGExihydZbwGKVaX3So0tgiCA58lQjl+9GuO3UBeSgkhGLr9zQbUF0FVVOH5BQm54NCD8+Pr1Rx7soSGAizgMMMWeqSddV/aX0ZYAC3+hNwN3OnW5MQBTbFFvADAJff99wIVUAyBNwCyA2wCwSDm9D4D5SoVUYaKMKSVM4ARN2NTSBrHnAm4Xvy60caw71GN0cgzTmUJgVvHeKnRjwA2h+GYPGP0UX3zHzsYupxSj//KvZffe+6yp+EQfzjZ6ADOlGiyxvy+oDENjAGNoLVHfYjTm5+ku7L40iM7k+baHk8khmmNFyuTwf0r+IzFs4Qm8YJPg+eocIEIDQNd6pl8IoIcAoA/PdgFPUt9SBofqmg/U/GDrZmCekOx9Jt5wcXWV1HyAuAGVrTNgnnB73OMLQhh2kOa4j3vnwZptTResvj0uiTcgXUg6lKDlBaI+WNaEhXiCXr3TVZkR6fgtO5zgkQtltwNQoOGerrhdEV/wLCfgK369fbvkIyYKvlNUlWbaU1MCuht7l2r3PHDMeXDIeueupEsW+YABmLoCi9y+D4gr1JwBq5YTiD4v2AhAXAGv4w4Ax5FD6D5lBh2+QPd7zQlsFLency8GjmUsnAfrl/EvoaDKQ2XYnPEAAAAASUVORK5CYII=");
    }
}

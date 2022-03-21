package cn.lliiooll.mt.service;

import cn.lliiooll.mt.pojo.Profile;
import cn.lliiooll.mt.pojo.UserRole;

import java.util.List;

public interface ProfileService {


    List<Profile> findProfileByUid(String uid);

    List<Profile> findProfileByName(String name);

    List<Profile> findProfileByUUID(String uuid);

    List<Profile> findProfileByAccessToken(String accessToken);

    List<Profile> findProfileByClientToken(String clientToken);

    List<Profile> findProfileByServerId(String serverId);

    void createProfile(Profile profile);

    void updateProfile(Profile profile);
}

package cn.lliiooll.mt.mapper;

import cn.lliiooll.mt.pojo.Profile;
import cn.lliiooll.mt.pojo.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProfileMapper {


    List<Profile> findProfileByUid(String uid);

    Profile findProfileByName(String name);

    Profile findProfileByUUID(String uuid);

    Profile findProfileByAccessToken(String accessToken);

    List<Profile> findProfileByClientToken(String clientToken);

    List<Profile> findProfileByServerId(String serverId);

    void createProfile(Profile profile);

    void updateProfile(Profile profile);
}

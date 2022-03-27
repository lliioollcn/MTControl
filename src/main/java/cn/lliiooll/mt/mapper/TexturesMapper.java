package cn.lliiooll.mt.mapper;

import cn.lliiooll.mt.pojo.ProfileTextures;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TexturesMapper {

    ProfileTextures findTexturesByUid(String uid);

    ProfileTextures findTexturesByUuid(String uuid);

    void createTextures(ProfileTextures textures);

    void updateTextures(ProfileTextures textures);
}

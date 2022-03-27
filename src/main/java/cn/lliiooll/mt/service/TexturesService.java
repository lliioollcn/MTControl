package cn.lliiooll.mt.service;

import cn.lliiooll.mt.pojo.ProfileTextures;

public interface TexturesService {

    ProfileTextures findTexturesByUid(String uid);

    ProfileTextures findTexturesByUuid(String uuid);

    void createTextures(ProfileTextures textures);

    void updateTextures(ProfileTextures textures);
}

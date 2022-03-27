package cn.lliiooll.mt.service.impl;

import cn.lliiooll.mt.mapper.ProfileMapper;
import cn.lliiooll.mt.mapper.TexturesMapper;
import cn.lliiooll.mt.pojo.Profile;
import cn.lliiooll.mt.pojo.ProfileTextures;
import cn.lliiooll.mt.service.ProfileService;
import cn.lliiooll.mt.service.TexturesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TextureServiceImpl implements TexturesService {

    @Autowired
    private TexturesMapper mapper;

    @Override
    public ProfileTextures findTexturesByUid(String uid) {
        return mapper.findTexturesByUid(uid);
    }

    @Override
    public ProfileTextures findTexturesByUuid(String uuid) {
        return mapper.findTexturesByUuid(uuid);
    }

    @Override
    public void createTextures(ProfileTextures textures) {
        mapper.createTextures(textures);
    }

    @Override
    public void updateTextures(ProfileTextures textures) {
        mapper.updateTextures(textures);
    }
}

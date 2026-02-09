package ru.sicampus.bootcamp2026.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.Entity.Avatar;
import ru.sicampus.bootcamp2026.Repository.AvatarRepository;
import ru.sicampus.bootcamp2026.Service.AvatarService;

@Service
public class AvatarServiceImpl implements AvatarService {
    @Autowired
    private AvatarRepository avatarRepository;
    @Override
    public void createdAvatar(String url){
        if(avatarRepository.existsByName(url)){
            Avatar avatar=new Avatar(url);
            avatarRepository.save(avatar);
        }
    }
}

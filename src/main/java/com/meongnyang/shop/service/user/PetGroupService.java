package com.meongnyang.shop.service.user;

import com.meongnyang.shop.entity.PetGroup;
import com.meongnyang.shop.repository.PetGroupMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PetGroupService {
    private final PetGroupMapper petGroupMapper;

    public List<PetGroup> getPetGroups() {
        return petGroupMapper.findPetGroup();
    }
}

package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.JobTitleDTO;
import ru.sicampus.bootcamp2026.repository.JobTitleRepository;
import ru.sicampus.bootcamp2026.service.JobTitleService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobTitleServiceImpl implements JobTitleService {

    private final JobTitleRepository jobTitleRepository;

    @Override
    public List<JobTitleDTO> getAllJobTitles() {
        return jobTitleRepository.findAll().stream()
                .map(j -> new JobTitleDTO(j.getId(), j.getTitleName()))
                .toList();
    }
}

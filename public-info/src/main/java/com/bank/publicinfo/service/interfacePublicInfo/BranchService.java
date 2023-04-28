package com.bank.publicinfo.service.interfacePublicInfo;

import com.bank.publicinfo.entity.Branch;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BranchService {
    Branch createBranch(Branch Branch);
    Branch getBranchById(Long id);
    Branch updateBranch(Branch branch, Long id);
    void deleteBranch(Long id);
    List<Branch> getAllBranches();
}

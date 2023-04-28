package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.Branch;
import com.bank.publicinfo.exception.EntityNotFoundException;
import com.bank.publicinfo.repository.BranchRepository;
import com.bank.publicinfo.service.interfacePublicInfo.BranchService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;

    /** Метод для создание нового объекта Branch
     *
     * @param branch
     */
    @Override
    @Transactional
    public Branch createBranch(Branch branch) {
        log.info("Сведения об отделении банка были внесены в базу данных");
        return branchRepository.save(branch);
    }

    /** Метод для получения объекта Branch по его id
     *
     * @param id
     */
    @Override
    public Branch getBranchById(Long id) {
        Optional<Branch> branchFromBD = branchRepository.findById(id);
        if (branchFromBD.isEmpty()) {
            log.error("Сведения об отделении банка отсутствуют");
            throw new EntityNotFoundException("No branch found by id");
        }
        Branch branch1 = branchFromBD.get();
        log.info("Сведения об отделении банка с " + "id" + " получены из базы данных");
        return branch1;
    }

    /** Метод для изменения уже существующего объекта Branch
     *
     * @param branch, id
     */
    @Override
    @Transactional
    public Branch updateBranch(Branch branch, Long id) {
        Optional<Branch> branchFromBD = branchRepository.findById(id);
        if (branchFromBD.isEmpty()) {
            log.error("Сведения об отделениях банка отсутствуют");
            throw new EntityNotFoundException("No branch found by id");
        }
        Branch branch1 = branchFromBD.get();
        log.info("Сведения об отделении банка были изменены в базе данных");
        return branchRepository.save(branch);
    }

    /** Метод для удаления объекта Branch
     *
     * @param id
     */
    @Override
    @Transactional
    public void deleteBranch(Long id) {
        Optional<Branch> branchFromDB = branchRepository.findById(id);
        if (branchFromDB.isEmpty()) {
            log.error("Сведения об отделении банка отсутствуют");
            throw new EntityNotFoundException("No branch found by id");
        }
        log.info("Сведения об отделении банка были удалены из базы данных");
        branchRepository.deleteById(id);
    }

    /** Метод для получения списка всех объектов Branch
     *
     */
    @Override
    public List<Branch> getAllBranches() {
        List<Branch> allBranches = branchRepository.findAll();
        if (allBranches.isEmpty()) {
            log.error("Сведения об отделении банка отсутствуют");
            throw new EntityNotFoundException("branches list is empty");
        }
        log.info("Были получены сведения обо всех отделениях банка из базы данных");
        return allBranches;
    }
}

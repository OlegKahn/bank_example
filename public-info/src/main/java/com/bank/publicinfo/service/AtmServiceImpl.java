package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.Atm;
import com.bank.publicinfo.exception.EntityNotFoundException;
import com.bank.publicinfo.repository.AtmRepository;
import com.bank.publicinfo.service.interfacePublicInfo.AtmService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class AtmServiceImpl implements AtmService {

    private final AtmRepository atmRepository;

    /** Метод для создание нового объекта Atm
     *
     * @param atm
     */
    @Override
    @Transactional
    public Atm createAtm(Atm atm) {
//        log.info("Сведения о банкомате были внесены в базу данных");
        return atmRepository.save(atm);
    }

    /** Метод для получения объекта Atm по его id
     *
     * @param id
     */
    @Override
    public Atm getAtmById(Long id) {
        Optional<Atm> atmFromBD = atmRepository.findById(id);
        if (atmFromBD.isEmpty()) {
            log.error("Сведения о банкоматах отсутствуют");
            throw new EntityNotFoundException("No atm found by id");
        }
        Atm atm1 = atmFromBD.get();
        log.info("Сведения о банкомате с " + "id" + " получены из базы данных");
        return atm1;
    }

    /** Метод для изменения данных уже существующего объекта Atm
     *
     * @param atm, id
     */

    @Override
    @Transactional
    public Atm updateAtm(Atm atm, Long id) {
        Optional<Atm> atmFromBD = atmRepository.findById(id);
        if (atmFromBD.isEmpty()) {
            log.error("Сведения о банкоматах отсутствуют");
            throw new EntityNotFoundException("No atm found by id");
        }
        Atm atm1 = atmFromBD.get();
        atm1.setAddress(atm.getAddress());
        atm1.setStartOfWork(atm.getStartOfWork());
        atm1.setEndOfWork(atm.getEndOfWork());
        atm1.setBranchId(atm.getBranchId());
        log.info("Сведения о банкомате были изменены в базе данных");
        return atmRepository.saveAndFlush(atm1);
    }

    /** Метод для удаления объекта Atm
     *
     * @param id
     */

    @Override
    @Transactional
    public void deleteAtm(Long id) {
        Optional<Atm> atmFromDB = atmRepository.findById(id);
        if (atmFromDB.isEmpty()) {
            log.error("Сведения о банкоматах отсутствуют");
            throw new EntityNotFoundException("No Atm found by id");
        }
        log.info("Сведения о банкомате были удалены из базы данных");
        atmRepository.deleteById(id);
    }

    /** Метод для получения списка всех объектов Atm
     *
     */

    @Override
    public List<Atm> getAllAtm() {
        List<Atm> allAtm = atmRepository.findAll();
        if (allAtm.isEmpty()) {
            log.error("Сведения о банкоматах отсутствуют");
            throw new EntityNotFoundException("atm list is empty");
        }
        log.info("Были получены сведения обо всех банкоматах из базы данных");
        return allAtm;
    }
}

package com.backend.passthemon.repositoryimpl;

import com.backend.passthemon.dao.VerificationDao;
import com.backend.passthemon.entity.Verification;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class VerificationRepositoryImplUnitTest {
    @Mock
    VerificationDao verificationDao;

    @InjectMocks
    VerificationRepositoryImpl verificationRepository;

    private Verification verification = new Verification();

    private String email = "391678792hd@sjtu.edu.cn";

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        Mockito.when(verificationDao.save(verification)).thenReturn(verification);
        Mockito.when(verificationDao.getVerificationByEmail(email)).thenReturn(verification);
    }
    @Test
    public void save() {
        Assert.assertEquals(verificationRepository.save(verification), verification);
        Mockito.verify(verificationDao).save(verification);
    }
    @Test
    public void getVerificationByEmail() {
        Assert.assertEquals(verificationRepository.getVerificationByEmail(email), verification);
        Mockito.verify(verificationDao).getVerificationByEmail(email);
    }
}

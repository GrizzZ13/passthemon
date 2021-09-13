package com.backend.passthemon.serviceimpl;

import com.backend.passthemon.entity.Verification;
import com.backend.passthemon.repository.VerificationRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class VerificationServiceImplUnitTest {
    @Mock
    VerificationRepository verificationRepository;

    @InjectMocks
    private VerificationServiceImpl verificationService;

    private Verification verification;
    private String email = "391678792hd@sjtu.edu.cn";

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        Mockito.when(verificationRepository.save(verification)).thenReturn(verification);
        Mockito.when(verificationRepository.getVerificationByEmail(email)).thenReturn(verification);
    }

    @Test
    public void save() {
        Assert.assertEquals(verificationService.save(verification), verification);
        Mockito.verify(verificationRepository).save(verification);
    }
    @Test
    public void getVerificationByEmail() {
        Assert.assertEquals(verificationService.getVerificationByEmail(email), verification);
        Mockito.verify(verificationRepository).getVerificationByEmail(email);
    }
}

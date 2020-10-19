package org.slovob.slovoborg.opinion;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slovob.slovoborg.definition.Definition;
import org.slovob.slovoborg.definition.DefinitionRepository;
import org.slovob.slovoborg.exception.FishyFrontendQuery;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class OpinionServiceTest {
    public static final String CLIENT_IP = "127.0.0.1";
    public static final long DEFINITION_ID = 1;

    private OpinionService subject;
    private OpinionTransfer opinionTransfer;

    @Mock
    private OpinionRepository opinionRepo;

    @Mock
    private DefinitionRepository definitionRepo;

    @BeforeEach
    public void init() {
        initMocks(this);
        subject = new OpinionService(opinionRepo, definitionRepo);
        opinionTransfer = new OpinionTransfer(DEFINITION_ID, 1); // all tests will try to like first definition
    }

    @Test
    public void shouldFail_ifNoSuchDefinition() {
        given(definitionRepo.findById(DEFINITION_ID)).willReturn(Optional.empty());

        Assertions.assertThrows(FishyFrontendQuery.class, () -> {
            subject.processOpinion(opinionTransfer, CLIENT_IP);
        });
    }

    @Test
    public void shouldLike_ifNoOpinionYet() {
        given(definitionRepo.findById(DEFINITION_ID)).willReturn(Optional.of(new Definition()));
        Optional<Opinion> existingOpinion = Optional.empty();
        given(opinionRepo.findByDefinitionIdAndIpAddress(DEFINITION_ID, CLIENT_IP)).willReturn(existingOpinion);

        subject.processOpinion(opinionTransfer, CLIENT_IP);

        Definition definition = new Definition();
        definition.like();
        verify(definitionRepo).save(definition);
        verify(opinionRepo).save(new Opinion(opinionTransfer.getOpinion(), opinionTransfer.getDefinitionId(), CLIENT_IP));
    }

    @Test
    public void shouldUnlike_ifAlreadyLiked() {
        given(definitionRepo.findById(DEFINITION_ID)).willReturn(Optional.of(new Definition()));
        Optional<Opinion> existingOpinion = Optional.of(new Opinion(1, DEFINITION_ID, CLIENT_IP));
        given(opinionRepo.findByDefinitionIdAndIpAddress(DEFINITION_ID, CLIENT_IP)).willReturn(existingOpinion);

        subject.processOpinion(opinionTransfer, CLIENT_IP);

        Definition definition = new Definition();
        definition.unlike();
        verify(definitionRepo).save(definition);
        verify(opinionRepo).deleteById(0L);
    }

    @Test void shouldLikeAndUndislike_ifAlreadyDisliked() {
        given(definitionRepo.findById(DEFINITION_ID)).willReturn(Optional.of(new Definition()));
        Optional<Opinion> existingOpinion = Optional.of(new Opinion(-1, DEFINITION_ID, CLIENT_IP));
        given(opinionRepo.findByDefinitionIdAndIpAddress(DEFINITION_ID, CLIENT_IP)).willReturn(existingOpinion);

        subject.processOpinion(opinionTransfer, CLIENT_IP);

        Definition definition = new Definition();
        definition.like();
        definition.undislike();
        verify(definitionRepo).save(definition);
        verify(opinionRepo).save(new Opinion(opinionTransfer.getOpinion(), opinionTransfer.getDefinitionId(), CLIENT_IP));
    }
}

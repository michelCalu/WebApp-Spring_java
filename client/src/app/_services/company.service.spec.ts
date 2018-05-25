import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { Router } from '@angular/router';
import { CompanyService, AlertService } from '../_services';
import { Mandatary, Citizen } from '../_models' ;
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RouterTestingModule } from '@angular/router/testing';
import { TranslateService } from '@ngx-translate/core';

describe('Service : Company', () => {
  
  let injector: TestBed;
  let service: CompanyService;
  let httpMock: HttpTestingController;
  
  /*
   * Mocking TranslateService, because its dependency TranslateStore cannot be regularly imported for some reason. 
   */
  class MockTranslateService {};

  beforeEach(() => {
  
   TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule],
      providers: [CompanyService, AlertService, {provide: TranslateService, useClass: MockTranslateService}]
    }).compileComponents;
    injector = getTestBed();
    service = injector.get(CompanyService);
    httpMock = injector.get(HttpTestingController);
    });
  
  afterEach(() => {
  httpMock.verify();
  });
  
  it('#getMandataries should return an Observable<Mandatary[]>', () => {
    
    let citizen = new Citizen();
    citizen.id = 5;
    let m1 = new Mandatary();
    m1.citizen = citizen;
    let m2 = new Mandatary();
    m2.citizen = citizen;
    let mockedMandataries = [m1,m2];
    
    service.getMandataries(5).subscribe(mandataries => {
      expect(mandataries.length).toBe(2);
      expect(mandataries).toEqual(mockedMandataries);
    });
    
    const req = httpMock.expectOne('/mandataries?citizenId=5');
    expect(req.request.method).toBe("GET");
    expect(req.request.params.has('citizenId'));
    req.flush(mockedMandataries);
  });
});

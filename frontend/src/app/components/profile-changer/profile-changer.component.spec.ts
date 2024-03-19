import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileChangerComponent } from './profile-changer.component';

describe('ProfileChangerComponent', () => {
  let component: ProfileChangerComponent;
  let fixture: ComponentFixture<ProfileChangerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfileChangerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ProfileChangerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

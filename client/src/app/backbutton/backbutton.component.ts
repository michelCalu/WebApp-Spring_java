import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'backbutton',
  templateUrl: './backbutton.component.html',
})
export class BackbuttonComponent implements OnInit {
  
  @Input() goBackRoute: string;
    

  constructor(private router: Router) { }

  ngOnInit() {
  }
  
  goBack(): void {
    this.router.navigate([this.goBackRoute]);
  }

}

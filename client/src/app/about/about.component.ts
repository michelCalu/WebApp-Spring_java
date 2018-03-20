import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AboutService } from './about.service';

@Component({
  templateUrl: './about.component.html',
})
export class AboutComponent implements OnInit {

  about: any;

  constructor(private router: Router, private aboutService: AboutService) {

  }

  ngOnInit(): void {
    this.aboutService.getAbout().subscribe(data => {
      this.about = data;
    });
  }

}
